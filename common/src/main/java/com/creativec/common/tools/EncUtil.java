package com.creativec.common.tools;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * 编码工具类
 */
public class EncUtil {

    private static final String GBK = "GBK";
    private static final String ISO8859 = "ISO-8859-1";

    /**
     * 字符串转ISO-8859-1
     * @param str 字符串
     * @return ISO-8859-1字符串
     */
    public static String toIso(String str) {
        try {
            return base64(new String(str.getBytes(GBK), ISO8859));
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * ISO-8859-1字符串转换
     * @param iso ISO-8859-1字符串
     * @return 字符串
     */
    public static String fromIso(String iso) {
        try {
            return new String(fromBase64(iso).getBytes(ISO8859), GBK);
        } catch (UnsupportedEncodingException e) {
            return iso;
        }
    }

    /**
     * 转换为BASE64编码
     * @param s 待转换串
     * @return BASE编码后的串
     */
    public static String base64(String s) {
        if (s == null) {
            return s;
        }
        Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(s.getBytes());
    }

    /**
     * 从BASE64编码转回
     * @param base64 BASE64编码串
     * @return 转回的串
     */
    public static String fromBase64(String base64) {
        if (base64 == null) {
            return "";
        }
        Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(base64));
    }
}
