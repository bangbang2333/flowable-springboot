package com.creativec.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.creativec.base.BaseConstant;
import com.creativec.entity.SysUser;
import com.creativec.exception.BusinessException;
import com.creativec.mapper.SysUserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Cache;

import java.util.*;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhongshengxiang
 * @since 2020-03-13
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {
    @Autowired private Cache<Integer, List<String>> permissionCache;

    public String login(String username, String password) {
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
            return generateToken(claims);
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

    public List<String> getUserPermissions(Integer id) {
        List<String> permissions = permissionCache.getIfPresent(id);
        if (permissions == null) {
            ArrayList<String> objects = new ArrayList<>();
            objects.add("/sysUser/getInfo");
            permissionCache.put(id, objects);
            return new ArrayList<>();
        }
        return permissions;
    }
}
