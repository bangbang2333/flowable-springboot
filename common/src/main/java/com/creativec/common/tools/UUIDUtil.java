package com.creativec.common.tools;

import java.util.UUID;

/**
 * @Description UUID工具类
 */
public class UUIDUtil {
	
	/**
	 * @Author xiongxiaofei
	 * @Date 2017/7/13
	 * @Description 生成UUID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}
}
