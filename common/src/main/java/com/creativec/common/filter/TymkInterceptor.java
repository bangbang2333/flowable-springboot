package com.creativec.common.filter;

import com.creativec.common.base.BaseConstant;
import com.creativec.common.base.GlobalResponse;
import com.creativec.common.base.IgnoreAuth;
import com.creativec.common.tools.JsonHelper;
import com.creativec.common.tools.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * 请求全局拦截器
 * 处理所有请求
 */
@Slf4j
@Component
public class TymkInterceptor implements HandlerInterceptor {

    @Autowired private RedisHelper redisHelper;
    @Value("${spring.application.default-token}") private String defaultToken;
    @Value("${spring.profiles.active}") private String active;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 对自定义注解处理  是否放行
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(IgnoreAuth.class)) {
                IgnoreAuth la = method.getAnnotation(IgnoreAuth.class);
                if (la.isIngore()) {
                    return true;
                }
            }
        }
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            if ("local".equals(active)) {
                token = defaultToken;
            } else {
                return outMsg(response, "请传递token", 401);
            }
        }
        Object str = redisHelper.get(token);
        if (str == null) {
            return outMsg(response, "token已失效,请重新登录", -4000);
        }
        redisHelper.expire(token, BaseConstant.tokenExpire);
        request.setAttribute("userInfo", str);
        return true;
    }

    public boolean outMsg(HttpServletResponse response, String msg, Integer recode) {
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(JsonHelper.toJson(GlobalResponse.fail(msg, recode)));
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            return false;
        } catch (IOException e) {
            log.error("\n outMsg exception {}:", msg);
        }
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
