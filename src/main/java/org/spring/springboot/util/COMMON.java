package org.spring.springboot.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class COMMON {
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		if (obj instanceof String) {
			if ("null".equals(((String) obj).toLowerCase())) {
				return true;
			}
			return ((String) obj).trim().equals("");
		}
		if (obj instanceof Collection) {
			Collection coll = (Collection) obj;
			return (coll.size() == 0);
		}
		if (obj instanceof Map) {
			Map map = (Map) obj;
			return (map.size() == 0);
		}
		if (obj.getClass().isArray()) {
			return (Array.getLength(obj) == 0);
		}

		return false;
	}
	public static boolean isDigitalString(String str) {
		if (isEmpty(str))
			return false;
		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
}
