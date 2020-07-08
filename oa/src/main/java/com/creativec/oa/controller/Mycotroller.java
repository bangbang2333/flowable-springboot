package com.creativec.oa.controller;

import com.creativec.base.AuthDataHolder;
import com.creativec.base.BaseConstant;
import com.creativec.base.IgnoreAuth;
import com.creativec.exception.BusinessException;
import com.creativec.oa.service.SysUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sysUser")
@Api(tags = {"用户相关接口"})
public class Mycotroller {
    @Autowired private SysUserService sysUserService;

    @IgnoreAuth
    @ApiOperation(notes = "login", value = "用户登录")
    @PostMapping("/login")
    public String login(@RequestParam String username, String password) {
        return sysUserService.login(username, password);
    }

    @IgnoreAuth
    @ApiOperation(notes = "refreshToken", value = "刷新用户token")
    @GetMapping("/refreshToken")
    public String refreshToken(HttpServletRequest request) {
        String token = request.getHeader(BaseConstant.AUTH);
        if (token != null && token.startsWith(BaseConstant.BEARER)) {
            String authToken = token.substring(7);
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
}
