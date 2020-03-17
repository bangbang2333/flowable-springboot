package com.creativec.common.tools;

import org.springframework.util.DigestUtils;

public class Md5Util {

    //盐，用于混交md5
    private static final String slat = "&%5123***&&%%$$#@";
    /**
     * 生成md5
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 生成md5
     * @param str
     * @return
     */
    public static String getMD5IncludeSlat(String str, String slatStr) {
        if (slatStr == null || slatStr.isBlank()) {
            slatStr = slat;
        }
        String base = str + "/" + slatStr;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
