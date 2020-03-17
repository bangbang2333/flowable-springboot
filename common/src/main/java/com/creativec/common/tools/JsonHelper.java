package com.creativec.common.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * json工具类,提供常用的工具方法
 * 
 * @author daizy
 * 
 */
public class JsonHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonHelper.class);
	/**
	 * 默认的日期格式
	 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 单例模式
	 */
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();



	/**
	 * 转换为json.
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		if (null == object) {
			return "";
		}
		try {
			return OBJECT_MAPPER.writeValueAsString(object);
		} catch (IOException e) {
			logger.error("write to json string error:" + object, e);
			return null;
		}
	}


	/**
	 * json转换为对象.
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) {
		
		if (Strings.isNullOrEmpty(jsonString)) {
			return null;
		}
		try {
			return OBJECT_MAPPER.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.error("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * Description ： 将map转换为对象<br>
	 * 
	 * daizy
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 * @since
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T map2obj(Map map, Class<T> clazz) {
		return JsonHelper.fromJson(JsonHelper.toJson(map), clazz);
	}

	/**
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap obj2map(Object obj) {
		return JsonHelper.fromJson(JsonHelper.toJson(obj), HashMap.class);
	}

	/**
	 * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需读取集合如List/Map, 且不是List<String>这种简单类型时,先使用函數constructParametricType构造类型.
	 * 
	 * @see #constructParametricType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonString, JavaType javaType) {
		if (Strings.isNullOrEmpty(jsonString)) {
			return null;
		}
		try {
			return (T) OBJECT_MAPPER.readValue(jsonString, javaType);
		} catch (IOException e) {
			logger.error("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 
	 * @param jsonString
	 * @param parametrized
	 *            泛型类
	 * @param parameterClasses
	 *            泛型参
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonString, Class<?> parametrized,
                                 Class<?>... parameterClasses) {
		if (Strings.isNullOrEmpty(jsonString)) {
			return null;
		}
		return (T) fromJson(jsonString,
				constructParametricType(parametrized, parameterClasses));
	}

	/**
	 * 構造泛型的Type如List<MyBean>,
	 * 则调用constructParametricType(ArrayList.class,MyBean.class)
	 * Map<String,MyBean>则调用(HashMap.class,String.class, MyBean.class)
	 */
	public static JavaType constructParametricType(Class<?> parametrized,
                                                   Class<?>... parameterClasses) {
		return OBJECT_MAPPER.getTypeFactory().constructParametricType(
				parametrized, parameterClasses);
	}

	/**
	 * 當JSON裡只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T update(T object, String jsonString) {
		try {
			return (T) OBJECT_MAPPER.readerForUpdating(object).readValue(
					jsonString);
		} catch (JsonProcessingException e) {
			logger.error("update json string:" + jsonString + " to object:"
					+ object + " error.", e);
		} catch (IOException e) {
			logger.error("update json string:" + jsonString + " to object:"
					+ object + " error.", e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static String getJsonValue(String rescontent, String key) {
		String v = null;
		try {
			Map<String, Object> map = JsonHelper.fromJson(rescontent, Map.class);
			v = String.valueOf(map.get(key));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return v;
	}

}
