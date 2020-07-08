package com.creativec.base;

import lombok.*;

/**
 * @author ZSX
 */
public class AuthDataHolder {

    private static final ThreadLocal<User> dataThreadLocal = new ThreadLocal<>();

    public static User get() {
        User data = dataThreadLocal.get();
        if (data == null) {
            data = new User();
            dataThreadLocal.set(data);
        }
        return data;
    }


    public static void clear() {
        dataThreadLocal.remove();
    }


    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @ToString
    public static class User {
        private Integer id;
        private String userName;
        private String phone;
    }
}
