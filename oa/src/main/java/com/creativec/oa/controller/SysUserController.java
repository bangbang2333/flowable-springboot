package com.creativec.oa.controller;

import com.creativec.base.*;
import com.creativec.entity.SysMenu;
import com.creativec.entity.SysRole;
import com.creativec.exception.BusinessException;
import com.creativec.oa.dto.AddMenu2RoleDto;
import com.creativec.oa.dto.AddRole2UserDto;
import com.creativec.oa.service.SystemService;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mdm")
@Api(tags = {"主数据相关接口"})
public class SysUserController {
    @Autowired private SystemService sysUserService;

    @IgnoreAuth
    @ApiOperation(notes = "login", value = "用户登录")
    @PostMapping("/login")
    public Map login(@RequestParam String username, String password) {
        return sysUserService.login(username, password);
    }

    @IgnoreAuth
    @ApiOperation(notes = "refreshToken", value = "刷新用户token")
    @GetMapping("/refreshToken")
    public String refreshToken(HttpServletRequest request) {
        String token = request.getHeader(BaseConstant.AUTH);
        if (token != null && token.startsWith(BaseConstant.BEARER)) {
            String authToken = StringUtils.substringAfter(token, BaseConstant.BEARER);
            Claims claims = Jwts.parser()
                    .setSigningKey(BaseConstant.SECRET)
                    .parseClaimsJws(authToken)
                    .getBody();
            return sysUserService.refreshToken(claims.get("id").toString());
        } else {
            throw new BusinessException("Access Token is invalid", 400);
        }
    }

    @ApiOperation(notes = "getInfo", value = "用户信息")
    @PostMapping("/getInfo")
    public AuthDataHolder.User getInfo() {
        return AuthDataHolder.get();
    }

    @ApiOperation(notes = "saveOrUpdateRole", value = "新增或修改角色")
    @PostMapping("/saveOrUpdateRole")
    public boolean saveOrUpdateRole(@Valid @RequestBody SysRole role) {
        return sysUserService.saveOrUpdateRole(role);
    }

    @ApiOperation(notes = "removeRole", value = "删除角色")
    @PostMapping("/removeRole")
    public boolean removeRole(Integer id) {
        return sysUserService.removeRole(id);
    }

    @ApiOperation(notes = "saveOrUpdateMenu", value = "新增或修改菜单")
    @PostMapping("/saveOrUpdateMenu")
    public boolean saveOrUpdateMenu(@Valid @RequestBody SysMenu menu) {
        return sysUserService.saveOrUpdateMenu(menu);
    }

    @ApiOperation(notes = "removeMenu", value = "删除菜单")
    @PostMapping("/removeMenu")
    public boolean removeMenu(Integer id) {
        return sysUserService.removeMenu(id);
    }

    @ApiOperation(notes = "addMenu2Role", value = "给角色赋予菜单")
    @PostMapping("/addMenu2Role")
    public boolean addMenu2Role(@Valid @RequestBody AddMenu2RoleDto menu) {
        return sysUserService.addMenu2Role(menu);
    }

    @ApiOperation(notes = "addRole2User", value = "给用户赋予角色")
    @PostMapping("/addRole2User")
    public boolean addMenu2Role(@Valid @RequestBody AddRole2UserDto role) {
        return sysUserService.addRole2User(role);
    }

    @IgnoreAuth(isIngore = false)
    @ApiOperation(notes = "findRoleByUser", value = "给用户赋予角色时,查询所有角色,并且勾上已有角色")
    @PostMapping("/findRoleByUser")
    public List<SysRole> findRoleByUser(Integer userId) {
        return sysUserService.findRoleByUser(userId);
    }

    @IgnoreAuth(isIngore = false)
    @ApiOperation(notes = "findMenuByRole", value = "给角色赋予菜单时,查询所有菜单,并且勾上已有菜单")
    @PostMapping("/findMenuByRole")
    public List<SysMenu> findMenuByRole(Integer roleId) {
        return sysUserService.findMenuByRole(roleId);
    }

    @ApiOperation(notes = "findUserPage", value = "分页查询用户列表")
    @PostMapping("/findUserPage")
    public PageResult findUserPage(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return PageResult.getPageResult(sysUserService.list());
    }

    @ApiOperation(notes = "findAllRole", value = "查询角色列表,返回所有角色")
    @PostMapping("/findAllRole")
    public List<SysRole> findAllRole() {
        return sysUserService.findAllRole();
    }

    @ApiOperation(notes = "findAllMenu", value = "查询菜单列表(返回树形菜单)")
    @PostMapping("/findAllMenu")
    public List<SysMenu> findAllMenu() {
        return sysUserService.findAllMenu();
    }


    @IgnoreAuth(isIngore = false)
    @ApiOperation(notes = "getMenuByUser", value = "用户获取自己有哪些菜单")
    @PostMapping("/getMenuByUser")
    public List<SysMenu> getMenuByUser() {
        return sysUserService.getMenuByUser(AuthDataHolder.get().getId());
    }
}
