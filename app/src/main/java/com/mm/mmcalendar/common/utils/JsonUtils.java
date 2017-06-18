package com.mm.mmcalendar.common.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json转换工具类
 * 
 * @author yb
 * 
 */
public class JsonUtils {

	public static Gson gson;

	static {
		gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
	}
	
	/**
	 * json字符串转单个简单对象
	 * @param <T>
	 * 
	 * @param jsonStr
	 * @param t
	 *            Order.class
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromJson(String jsonStr, Class<T> t) throws Exception {
		return gson.fromJson(jsonStr, t);	
	}
	
	/**
	 * json字符串转单个复杂对象
	 * 
	 * @param jsonStr
	 * @param type
	 *            new TypeToken<JsonResult<Order>>() { }.getType()
	 * @return
	 * @throws Exception
	 */
	public static <T> T fromJson(String jsonStr, Type type) throws Exception {
		return gson.fromJson(jsonStr, type);
	}

	public static String toJson(Object t) {
		return gson.toJson(t);
	}

}
