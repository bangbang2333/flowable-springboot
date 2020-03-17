package com.creativec.common.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**

 * 消息签名工具

 */

public class SignatureUtil {

    /**
     * 获取签名
     * @param message
     * @param timestamp
     * @param nonce
     * @return
     */
    public static String getSignature(String message, Long timestamp, String nonce) {
        List<String> list = new ArrayList<>();
        list.add(message);
        list.add(timestamp.toString());
        list.add(nonce);
        Collections.sort(list);//对list集合进行排序
        String sortResult = list.toString();
        return Sha1Util.getSha1(sortResult);
    }
}