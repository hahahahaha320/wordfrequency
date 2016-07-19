package com.oolong.stat.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ImsiUtil {
	
	private static Map<String,Integer> imsiMap = new HashMap<String,Integer>();
	static 	{
		imsiMap.put("46000",1);imsiMap.put("46002",1);imsiMap.put("46007",1);  
		imsiMap.put("46001",2);imsiMap.put("46006",2);
		imsiMap.put("46003",3);imsiMap.put("46005",3);imsiMap.put("46020",3);
	}
	
	//运营商：1:移动，2:联通，3:电信,4:其他
	public static Integer getOperatorByImsi(String imsi)	{
		if(StringUtils.isEmpty(imsi) || imsi.length() < 6)	{
			return 4;
		} else {
			String pre = imsi.substring(0,5);
			Integer operator = imsiMap.get(pre);
			if(operator == null)	{
				operator = 4;
			}
			return operator;
		}
	}
}
