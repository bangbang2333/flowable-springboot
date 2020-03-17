package com.creativec.common.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象工具类
 * @author xiangyu
 *
 */
public class ObjectUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(ObjectUtil.class);

	/**
	 * 字节数组转换对象-反序列化
	 * @param bytes 对象序列化字节数组
	 * @return 反序结果对象
	 */
	public static Object ByteToObject(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bi =null;
		ObjectInputStream oi=null;
		try {
			// bytearray to object
			 bi = new ByteArrayInputStream(bytes);
			 oi = new ObjectInputStream(bi);

			obj = oi.readObject();

			bi.close();
			oi.close();
		} catch (Exception e) {
			logger.error("translation" + e.getMessage(), e);
			
		}finally{
			try {
				if (bi != null) {
				    bi.close();
                }
				if (oi != null) {
				    oi.close();
                }
			} catch (IOException e) {
				logger.error("translation" + e.getMessage(), e);
			}
		}
		return obj;
	}

	/**
	 * 对象转换字节数组-序列化
	 * @param obj 待序列化对象
	 * @return 序列化结果数组
	 */
	public static byte[] ObjectToByte(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bo=null;
		ObjectOutputStream oo =null;
		try {
			// object to bytearray
			 bo = new ByteArrayOutputStream();
			 oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);

			bytes = bo.toByteArray();

			bo.close();
			oo.close();
		} catch (Exception e) {
			logger.error("translation" + e.getMessage(), e);
		}finally{
			try {
				if (bo != null) {
				    bo.close();
                }
				if (oo != null) {
				    oo.close();
                }
			} catch (IOException e) {
				logger.error("translation" + e.getMessage(), e);
			}
		}
		return (bytes);
	}

	/**
	 * 对象XML表现
	 * @param obj 待序列化对象
	 * @return XML表现
	 */
	public static String toXmlString(Serializable obj) {
		if (obj == null) {
			return "<null/>";
		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(out));
		encoder.writeObject(obj);
		encoder.close();
		return out.toString();
	}
	
	/**
	 * @param xml 待反序列化的串
	 * @return 从XML反序列化出的对象
	 */
	public static Object fromXml(String xml) {
		if (xml == null) {
			return null;
		}
		InputStream is = null;
		XMLDecoder decoder = null;
		Object obj = null;
		try {
			is = new ByteArrayInputStream(xml.getBytes());
			decoder = new XMLDecoder(new BufferedInputStream(is));
			obj = decoder.readObject();
		} catch (Throwable t){
			logger.warn("Read object from XML fail: " + xml, t);
		} finally {
			if (decoder != null) {
				try {
					decoder.close();
				} catch (Exception ex) {}
			}
			if (is != null) {
				try {
					is.close();
				} catch (Exception ex) {}
			}
		}
		return obj;
	}

	public static  <T> List<T> castList(Object obj, Class<T> clazz)
	{
		if (obj == null) {
			return null;
		}
		else {
			List<T> result = new ArrayList<T>();
			if (obj instanceof List<?>) {
				for (Object o : (List<?>) obj) {
					result.add(clazz.cast(o));
				}
				return result;
			}
			return null;
		}
	}
}
