package com.creativec.common.base;

import com.creativec.common.tools.JsonHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ZSX
 */
public class AuthDataHolder {

    private static final ThreadLocal<User> dataThreadLocal = new ThreadLocal<>();

    public static User get() {
        User data = dataThreadLocal.get();
        if (data == null) {
            data = init();
            dataThreadLocal.set(data);
        }
        return data;
    }

    public static void clear() {
        dataThreadLocal.remove();
    }

    private static User init() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String userInfo = (String) request.getAttribute("userInfo");
        User user = JsonHelper.fromJson(userInfo, User.class);
        user.setIp(request.getRemoteAddr());
        System.out.println("=========AuthDataHolder.get()=========");
        return user;
    }


    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class User {
        String kid;
        String userName;
        @JsonIgnore
        String ip;
    }
}
