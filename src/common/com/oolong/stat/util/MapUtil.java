package com.oolong.stat.util;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
	public static Map<String, Object> getMap(String key1, Object value1) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put(key1, value1);
		return paraMap;
	}
	public static Map<String, Object> getMap(String key1, Object value1,
			String key2, Object value2) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put(key1, value1);
		paraMap.put(key2, value2);
		return paraMap;
	}
	public static Map<String, Object> getMap(String key1, Object value1,
			String key2, Object value2, String key3, Object value3) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put(key1, value1);
		paraMap.put(key2, value2);
		paraMap.put(key3, value3);
		return paraMap;
	}
}
