package com.creativec.base;

public interface BaseConstant {


    /**
     * token有效期,20小时
     */
    int TOKEN_EXPIRE = 3600 * 20 * 1000;

    /**
     * token有效期剩余少于10小时,就让刷新
     */
    long DIFF = 3600 * 10 * 1000;
    String SECRET = "123654";
    String AUTH = "Authorization";
    String BEARER = "Bearer ";

}
