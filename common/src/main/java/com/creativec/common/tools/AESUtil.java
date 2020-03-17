package com.creativec.common.tools;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/NoPadding";//默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @return 返回Base64转码后的加密数据
     */
    @SneakyThrows
    public static String encrypt(String encryptKey, String content) {
        if (content == null || content.isEmpty()) {
            return "";
        }

        //设置加密密钥
        String key = DigestUtils.md5DigestAsHex(encryptKey.getBytes()).substring(0, 16);
        //初始化向量
        String iv = DigestUtils.md5DigestAsHex(encryptKey.getBytes()).substring(16);

        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");

        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

        //创建密码器
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        int blockSize = cipher.getBlockSize();

        byte[] dataBytes = content.getBytes();
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
        }

        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

        //加密
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
        byte[] encrypted = cipher.doFinal(plaintext);// 加密

        return Base64.encodeBase64String(encrypted);//通过Base64转码返回
    }

    /**
     * AES 解密操作
     *
     * @param content 待解密内容
     * @return 返回Base64转码后的解密内容
     */
    @SneakyThrows
    public static String decrypt(String encryptKey, String content) {
        if (content == null || content.isEmpty()) {
            return null;
        }

        byte[] input = Base64.decodeBase64(content);

        //实例化
        Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

        String key = DigestUtils.md5DigestAsHex(encryptKey.getBytes()).substring(0, 16);
        //初始化向量
        String iv = DigestUtils.md5DigestAsHex(encryptKey.getBytes()).substring(16);

        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");

        IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

        //使用密钥初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);


        //执行操作
        byte[] result = cipher.doFinal(input);

        return new String(result, "utf-8")
                .replaceAll("\0", "")
                .replaceAll("", "")
                .replaceAll("\r", "");
    }
}
