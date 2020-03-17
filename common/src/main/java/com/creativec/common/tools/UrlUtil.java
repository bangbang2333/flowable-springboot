package com.creativec.common.tools;

import java.net.MalformedURLException;

public class UrlUtil {


    /**
     * 根据URL获取域名
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static String getDomainName(String url) throws MalformedURLException {
        if (url.contains("://")) {
            java.net.URL urlObject = new java.net.URL(url);
            return urlObject.getHost();// 获取主机名
        } else {
            if (url.contains(":")) {
                String[] temp = url.split(":");
                return temp[0];
            } else {
                return url;
            }
        }
    }

    /**
     * 根据URL获取协议
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static String getProtocol(String url) throws MalformedURLException {
        if (url.contains("://")) {
            java.net.URL urlObject = new java.net.URL(url);
            return urlObject.getProtocol();// 获取主机名
        } else {
           return "http";
        }
    }

    /**
     * 根据URL获取端口
     * @param url
     * @return
     * @throws MalformedURLException
     */
    public static Integer getPort(String url) throws MalformedURLException {
        if (url.contains("://")) {
            java.net.URL urlObject = new java.net.URL(url);
            return urlObject.getPort();// 获取主机名
        }
        else {
            if (url.contains(":")) {
                String[] temp = url.split(":");
                return Integer.getInteger(temp[1]);
            }
            else {
                return -1;
            }
        }
    }


    /**
     * 计算泛域名
     * @param domain
     * @return
     */
    public static String getWildcardDomain(String domain) {
        String[] temp = domain.split("\\.");//不能直接用.
        if (temp.length > 1) {
            String result = "*";
            for (int i = 1; i < temp.length; i++) {
                result = result.concat(".").concat(temp[i]);
            }
            return result;
        } else {
            return null;
        }
    }
}
