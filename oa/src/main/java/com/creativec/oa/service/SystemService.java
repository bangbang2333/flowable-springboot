package com.creativec.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creativec.base.BaseConstant;
import com.creativec.entity.*;
import com.creativec.exception.BusinessException;
import com.creativec.mapper.*;
import com.creativec.oa.dto.AddMenu2RoleDto;
import com.creativec.oa.dto.AddRole2UserDto;
import com.github.benmanes.caffeine.cache.Cache;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author zhongshengxiang
 * @since 2020-03-13
 */
@Service
public class SystemService extends ServiceImpl<SysUserMapper, SysUser> {
    @Autowired private Cache<String, List<Integer>> permissionCache;
    @Autowired private ManyTableMapper manyTableMapper;
    @Autowired private SysRoleMapper roleMapper;
    @Autowired private SysMenuService menuService;
    @Autowired private SysRoleMenuService roleMenuService;
    @Autowired private SysUserRoleService userRoleService;

    public Map login(String username, String password) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>()
                .eq("user_name", username)
                .last("limit 1");
        SysUser user = getOne(wrapper);
        if (user == null) {
            throw new BusinessException("用户不存在");
        } else {
            Map<String, Object> claims = new HashMap<>(2);
            claims.put("id", user.getId());
            claims.put("username", user.getUserName());
            HashMap<String, Object> map = new HashMap<>(2);
            map.put("menu", getMenuByUser(user.getId()));
            map.put("token", generateToken(claims));
            return map;
        }
    }


    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + BaseConstant.TOKEN_EXPIRE))
                .signWith(SignatureAlgorithm.HS512, BaseConstant.SECRET)
                .compact();
    }

    public String refreshToken(String kid) {
        SysUser user = getById(kid);
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("id", user.getId());
        claims.put("username", user.getUserName());
        return generateToken(claims);
    }

    /**
     * 查询访问资源所需角色
     */
    private List<Integer> getRoleByUri(String uri) {
        List<Integer> permission = permissionCache.getIfPresent(uri);
        if (permission != null) {
            return permission;
        }
        permission = manyTableMapper.getRoleByUri(uri);
        permissionCache.put(uri, permission);
        return permission;
    }


    /**
     * 查询是否有权限
     */
    public boolean isAllow(Integer userId, String uri) {
        List<Integer> roleUser = userRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", userId))
                .stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        List<Integer> roleUri = getRoleByUri(uri);
        return Collections.disjoint(roleUser, roleUri);
    }

    public List<SysMenu> getMenuByUser(Integer userId) {
        List<SysMenu> menu = manyTableMapper.getMenuByUser(userId);
        return menus2Tree(menu);
    }

    private List<SysMenu> initChild(List<SysMenu> menus, Map<Integer, List<SysMenu>> parent) {
        for (SysMenu m : menus) {
            List<SysMenu> child = parent.get(m.getId());
            if (child != null) {
                m.setChild(child);
                initChild(child, parent);
            }
        }
        return menus;
    }

    public List<SysRole> findRoleByUser(Integer userId) {
        List<SysRole> roles = roleMapper.selectList(null);
        List<SysUserRole> userRoles = userRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        if (userRoles.size() == 0) {
            return roles;
        }
        Map<Integer, Integer> map = userRoles.stream().collect(Collectors.toMap(SysUserRole::getRoleId, SysUserRole::getUserId));
        for (SysRole a : roles) {
            if (map.get(a.getId()) != null) {
                a.setChecked(true);
            }
        }
        return roles;
    }

    public List<SysMenu> findMenuByRole(Integer roleId) {
        List<SysMenu> menus = menuService.list();
        List<SysRoleMenu> roleMenus = roleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        Map<Integer, Integer> roleMenuMap = roleMenus.stream().collect(Collectors.toMap(SysRoleMenu::getMenuId, SysRoleMenu::getRoleId));
        Map<Integer, List<SysMenu>> parent = menus.stream().map(a -> {
            if (roleMenuMap.get(a.getId()) != null) {
                a.setChecked(true);
            }
            return a;
        }).collect(Collectors.groupingBy(SysMenu::getParentId));
        return initChild(parent.get(-1), parent);
    }

    public List<SysRole> findAllRole() {
        return roleMapper.selectList(null);
    }

    public List<SysMenu> findAllMenu() {
        List<SysMenu> menus = menuService.list();
        return menus2Tree(menus);
    }

    private List<SysMenu> menus2Tree(List<SysMenu> menus) {
        if (menus.size() == 0) {
            return menus;
        }
        Map<Integer, List<SysMenu>> parent = menus.stream().collect(Collectors.groupingBy(SysMenu::getParentId));
        return initChild(parent.get(-1), parent);
    }

    public boolean saveOrUpdateRole(SysRole role) {
        SysRole sysRole = roleMapper.selectOne(new QueryWrapper<SysRole>().eq("name", role.getName()).last("limit 1"));
        if (sysRole != null) {
            throw new BusinessException("角色名称已经存在");
        }
        return role.getId() == null ? roleMapper.insert(role) > 0 : roleMapper.updateById(role) > 0;
    }

    public boolean saveOrUpdateMenu(SysMenu menu) {
        permissionCache.invalidateAll();
        if (menu.getId() == null) {
            menu.setIsLeaf(true);
            if (menu.getParentId() == null || menu.getParentId() == -1) {
                menu.setLevel(1);
                menu.setParentId(-1);
                menu.setParentIdPath("/");
                return menuService.save(menu);
            }
            SysMenu parent = menuService.getById(menu.getParentId());
            if (parent.getIsLeaf()) {
                parent.setIsLeaf(false);
                menuService.updateById(parent);
            }
            menu.setLevel(parent.getLevel() + 1);
            menu.setParentIdPath(parent.getParentIdPath() + parent.getId() + "/");
            return menuService.save(menu);
        }
        return menuService.updateById(menu);
    }

    public boolean addMenu2Role(AddMenu2RoleDto menu) {
        roleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", menu.getRoleId()));
        List<SysRoleMenu> roleMenus = menu.getMenuIds().stream().map(a -> {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(menu.getRoleId());
            roleMenu.setMenuId(a);
            return roleMenu;
        }).collect(Collectors.toList());
        permissionCache.invalidateAll();
        return roleMenuService.saveBatch(roleMenus);
    }

    public boolean addRole2User(AddRole2UserDto role) {
        userRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", role.getUserId()));
        List<SysUserRole> userRoles = role.getRoleIds().stream().map(a -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(a);
            userRole.setUserId(role.getUserId());
            return userRole;
        }).collect(Collectors.toList());
        return userRoleService.saveBatch(userRoles);
    }

    public boolean removeRole(Integer id) {
        roleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
        userRoleService.remove(new QueryWrapper<SysUserRole>().eq("role_id", id));
        return roleMapper.deleteById(id) > 0;
    }

    public boolean removeMenu(Integer id) {
        SysMenu menu = menuService.getById(id);
        if (menu == null) {
            return false;
        }
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<SysMenu>().eq("id", id).or().like("parent_id_path", "/" + id + "/");
        List<Integer> ids = menuService.list(wrapper).stream().map(SysMenu::getId).collect(Collectors.toList());
        roleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("menu_id", ids));
        menuService.removeByIds(ids);
        if (menu.getParentId() != -1) {
            int count = menuService.count(new QueryWrapper<SysMenu>().eq("parent_id", menu.getParentId()));
            if (count == 0) {
                SysMenu parent = new SysMenu();
                parent.setId(menu.getParentId());
                parent.setIsLeaf(true);
                parent.setLevel(1);
                return menuService.updateById(parent);
            }
        }
        return true;
    }
}
