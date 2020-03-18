package com.creativec.example.controller;

import com.creativec.common.base.IgnoreAuth;
import com.creativec.common.base.PageResult;
import com.creativec.example.entity.SysUser;
import com.creativec.example.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysUser")
@Api(tags = {"用户相关接口"})
public class SysUserController {
    @Autowired private SysUserService sysUserService;

    @ApiOperation(notes = "getUserByPage", value = "分页查询用户")
    @GetMapping("/getUserByPage")
    public PageResult getUserByPage(SysUser user) {
        return sysUserService.getUserByPage(user);
    }

    @ApiOperation(notes = "removeUser", value = "删除用户")
    @GetMapping("/removeUser")
    public boolean removeUser(String kid) {
        return sysUserService.removeUser(kid);
    }

    @ApiOperation(notes = "update", value = "测试修改用户")
    @GetMapping("/update")
    public Boolean updateUser(SysUser user) {
        return sysUserService.updateUser(user);
    }

    @IgnoreAuth
    @ApiOperation(notes = "addUser", value = "添加用户")
    @PostMapping("/addUser")
    public String addUser(SysUser user) {
        return sysUserService.addUser(user);
    }

    @IgnoreAuth
    @ApiOperation(notes = "login", value = "用户登录")
    @PostMapping("/login")
    public String login(String username, String password) {
        return sysUserService.login(username, password);
    }
}
