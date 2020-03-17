package com.creativec.common.tools;

import lombok.NonNull;

import java.util.regex.Pattern;

public class SqlInjectionUtil {

    //正向判断主键字符：中线、数字、字母。安全性更高优先使用。
    final static Pattern idPattern = Pattern.compile("[-\\da-zA-Z]*");

    //final static Pattern xssPattern = Pattern.compile("[]*");


    /**
     * 正向检查符合ID规则防止SQL注入
     *
     * @param val
     * @return
     */
    public static String assertID(@NonNull String val) {
        String trimVal = val.trim();
        if (!idPattern.matcher(trimVal).matches()) {
            throw new RuntimeException("SqlInjection assertID [" + val + "] failure");
        }
        return val;
    }

    public static void main(String[] args) {
        SqlInjectionUtil.assertID(";truncate table");
    }
}
