package com.creativec.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creativec.base.BaseConstant;
import com.creativec.entity.*;
import com.creativec.exception.BusinessException;
import com.creativec.mapper.ManyTableMapper;
import com.creativec.mapper.SysRoleMapper;
import com.creativec.mapper.SysUserMapper;
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
        return menuService.toTree(menu);
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
        return menuService.toCheckedTree(menus, menu -> {
            if (roleMenuMap.get(menu.getId()) != null) {
                menu.setChecked(true);
            }
            return menu;
        });
    }

    public List<SysRole> findAllRole() {
        return roleMapper.selectList(null);
    }

    public List<SysMenu> findAllMenu() {
        List<SysMenu> menus = menuService.list();
        return menuService.toTree(menus);
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
        return menuService.saveOrUpdateTree(menu);
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

    public boolean removeRoleBatch(List<Integer> ids) {
        roleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("role_id", ids));
        userRoleService.remove(new QueryWrapper<SysUserRole>().in("role_id", ids));
        return roleMapper.deleteBatchIds(ids) > 0;
    }

    public boolean removeMenu(Integer id) {
        List<Integer> ids = menuService.removeTree(id);
        roleMenuService.remove(new QueryWrapper<SysRoleMenu>().in("menu_id", ids));
        return true;
    }

    public boolean removeUserBatch(List<Integer> ids) {
        userRoleService.remove(new QueryWrapper<SysUserRole>().in("user_id", ids));
        return removeByIds(ids);
    }
}
