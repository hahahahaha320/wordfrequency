package com.oolong.stat.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }
    public static String trimBlank(String str)    {
        return str.replaceAll("\\s*|\t|\r|\n","");
    }
    
    private static String getFixedLenthStr(char fill,int length)    {
        String temp = "";
        for(int i=0;i<length;i++)   {
            temp = temp + fill;
        }
        return temp;
    }
    //convert null string to ""
    public static String nullGuard(String str)   {
        if(str == null)    {
            return "";
        }
        return str;
    }
    public static String nullGuard(String str,String def)   {
        if(str == null || "".equals(str))    {
            return def;
        }
        return str;
    }
    // fill to fixed length before the string
    public static String fillBefore(String str,char fill,int length)  {
        str = nullGuard(str);
        int need = length - str.length();
        return getFixedLenthStr(fill,need) + str;
    }
    // fill to fixed length after the string
    public static String fillAfter(String str,char fill,int length)  {
        str = nullGuard(str);
        int need = length - str.length();
        return str + getFixedLenthStr(fill,need);
    }
    public static String formatStr(Object str) {
        return (str == null) ? "" : (String) str;
    }

    public static String deleteLast(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, str.length() - 1);
    }

    public static String deleteLast(String str, char c) {
        if (str == null || str.length() == 0) {
            return str;
        }
        if (str.charAt(str.length() - 1) == c) {
            return str.substring(0, str.length() - 1);
        }
        return str;
    }

    public static String getExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String getDateString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static String getDateString(long longTime, String format) {
        Date date = new Date(longTime);
        return getDateString(date, format);
    }
    public static String listToStr(List<String> strList)   {
        String idStr = "";
        for(String str : strList)   {
            idStr = idStr +  str + ",";
        }
        return deleteLast(idStr,',');
    }
    
    public static Integer string2Int(String str,Integer def){
		try	{
			return Integer.parseInt(str);
		} catch(Exception e)	{
			return def;
		}
	}
    public static Integer string2Int(String str){
		return string2Int(str, 0);
	}
    
    //String.split如果有key之间是空的话，会忽略掉，而且key在最后也会有问题。所以重写。
    public static String[] split(String content,String key){
		if(StringUtil.isEmpty(content) || StringUtil.isEmpty(key))	{
			return new String[] {content};
		}
		int pos = content.indexOf(key);
		List<String> strList = new ArrayList<String>();
		while(pos != -1)	{
			
			strList.add(content.substring(0,pos));
			content = content.substring(pos + key.length());
			pos = content.indexOf(key);
		}
		strList.add(content);
		
		String[] result = new String[strList.size()];
		for(int i=0;i<strList.size();i++)	{
			result[i] = strList.get(i);
		}
		return result;
	}
    
    public static String table2ModelName(String tableName) {
        String modelName = "";
        String[] strs = tableName.split("_");
        for(String str : strs)   {
            modelName = modelName + str.substring(0,1).toUpperCase() + str.substring(1);
        }
        return modelName;
    }
    
    public static void main(String[] args) {
    	String str = "_-_";
		String[] sa = split(str,"_-_");
		System.out.println(sa.length);
	}
}
