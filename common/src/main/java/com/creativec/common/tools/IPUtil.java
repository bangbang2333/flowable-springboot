package com.creativec.common.tools;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class IPUtil {

    /**
     * @Description 获取本机IP
     */
    public static String getLocalIp() {
        String ipAddress = "127.0.0.1";
        try {
            InetAddress address = InetAddress.getLocalHost();
            ipAddress = address.getHostAddress();
        } catch (Exception ex) {
//            ex.getMessage();
        }

        return ipAddress;
    }


    /**
     * @author xiongxiaofei
     * @date 2017/3/3
     * @intro 获取本机所有IPv4的地址
     */
    public static List<String> listLocalIp4() {
        List<String> localIp4List = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaceEnumeration;
        try {
            networkInterfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
            return localIp4List;
        }
        while (networkInterfaceEnumeration.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaceEnumeration.nextElement();
            Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
            while (inetAddressEnumeration.hasMoreElements()) {
                InetAddress inetAddress = inetAddressEnumeration.nextElement();
                if (inetAddress instanceof Inet4Address) {
                    if (!"127.0.0.1".equals(inetAddress.getHostAddress())) {
                        localIp4List.add(inetAddress.getHostAddress());
                    }
                }
            }
        }
        return localIp4List;
    }

    /**
     * @author xiongxiaofei
     * @date 2017/3/3
     * @intro 获取本机MAC地址
     */
    public static String getLocalMac() {
        // 获取网卡，获取地址
        byte[] mac;
        try {
            mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
            return null;
        }
//        log.info("mac数组长度：{}", mac.length);
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
//            log.info("每8位: {}", str);
            if (str.length() == 1) {
                sb.append("0").append(str);
            } else {
                sb.append(str);
            }
        }
//        log.info("本机MAC地址: {}", sb.toString().toUpperCase());
        return sb.toString().toUpperCase();
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        String unknown = "unknown";
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip != null && ip.length() > 0) {
            ip = StringUtils.split(ip, ",")[0];
        }
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
