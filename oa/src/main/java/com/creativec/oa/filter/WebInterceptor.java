package com.creativec.oa.filter;


import com.creativec.base.AuthDataHolder;
import com.creativec.base.BaseConstant;
import com.creativec.base.IgnoreAuth;
import com.creativec.exception.BusinessException;
import com.creativec.oa.service.SystemService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 请求全局拦截器
 * 处理所有请求
 */
@Component
public class WebInterceptor implements HandlerInterceptor {

    @Autowired private SystemService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 对自定义注解处理  是否放行
        boolean verify = true;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(IgnoreAuth.class)) {
                IgnoreAuth la = method.getAnnotation(IgnoreAuth.class);
                if (la.isIngore()) {
                    return true;
                } else {
                    //不校验资源权限,表示所有人都能访问
                    verify = false;
                }
            }
        }
        String token = request.getHeader(BaseConstant.AUTH);
        if (token != null && token.startsWith(BaseConstant.BEARER)) {
            String authToken = StringUtils.substringAfter(token, BaseConstant.BEARER);
            Claims claims;
            try {
                claims = Jwts.parser()
                        .setSigningKey(BaseConstant.SECRET)
                        .parseClaimsJws(authToken)
                        .getBody();
            } catch (ExpiredJwtException e) {
                throw new BusinessException("Access Token expired", 401);
            } catch (Exception e) {
                throw new BusinessException("Access Token is lawless", 400);
            }
            long diff = claims.getExpiration().getTime() - System.currentTimeMillis();
            if (diff < BaseConstant.DIFF) {
                throw new BusinessException("Access Token should be refreshed", 402);
            }
            //判断用户权限
            Integer id = (Integer) claims.get("id");
//            if (verify && sysUserService.isAllow(id, request.getRequestURI())) {
//                throw new BusinessException("无访问权限", 403);
//            }
            AuthDataHolder.User user = AuthDataHolder.get();
            user.setId(id);
            user.setUserName(claims.get("username").toString());
            return true;
        } else {
            throw new BusinessException("Access Token is invalid", 400);
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        AuthDataHolder.clear();
    }
}
