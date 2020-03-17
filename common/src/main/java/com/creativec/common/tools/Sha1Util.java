package com.creativec.common.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**

 * SHA1不可逆加密工具

 */

public class Sha1Util {
    private static Logger logger = Logger.getLogger(Sha1Util.class.getName());

    /**
     * 获取SHA1值
     * @param input
     * @return
     */
    public static String getSha1(String input)  {
        try {
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte single: result) {
                sb.append(Integer.toString((single & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.info(e.getMessage());
        }
        return null;
    }
}