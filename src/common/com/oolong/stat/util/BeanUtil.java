package com.oolong.stat.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtil {
	public static void populate(Object obj,Map<String,String> map)	{
		if(map == null || obj == null)	{
			return;
		}
		Class<?> clazz = obj.getClass();
		Map<String,Method> methodMap = new HashMap<String,Method>();
		Method[] methods = clazz.getMethods();
		for(Method method : methods){
			if(method.getName().startsWith("set") && method.getParameterTypes().length == 1)	{
				methodMap.put(method.getName()+method.getParameterTypes()[0].getSimpleName(), method);
			}
		}
		
		for (String key : map.keySet()) {
			try	{
			    String value = map.get(key);
			    String methodName = "set"+firstUpper(key);
			    Method method = methodMap.get(methodName + "String");
			    if(method != null)	{
			    	method.invoke(obj,value);
			    	continue;
			    } 
			    method = methodMap.get(methodName + "Integer");
			    if(method == null)	{
			    	method = methodMap.get(methodName + "int");
			    }
			    if(method != null)	{
			    	method.invoke(obj,Integer.parseInt(value));
			    	continue;
			    }
			    
			    
			    methodName = "set"+firstUpper(getJavaName(key));
			    method = methodMap.get(methodName + "String");
			    if(method != null)	{
			    	method.invoke(obj,value);
			    	continue;
			    } 
			    method = methodMap.get(methodName + "Integer");
			    if(method == null)	{
			    	method = methodMap.get(methodName + "int");
			    }
			    if(method != null)	{
			    	method.invoke(obj,Integer.parseInt(value));
			    	continue;
			    }
			} catch(Exception e)	{
			}
		}
	}
	private static String getJavaName(String propName) {
        String modelName = "";
        String[] strs = propName.split("_");
        for(int i=0;i<strs.length;i++)   {
            String str = strs[i];
            if(i==0)    {
                modelName = modelName + str.substring(0,1).toLowerCase() + str.substring(1);
            } else {
                modelName = modelName + str.substring(0,1).toUpperCase() + str.substring(1);
            }
        }
        return modelName;
    }
	private static String firstUpper(String str)   {
        if(str == null || str.length() < 1)	{
        	return str;
        }
        return str.substring(0,1).toUpperCase() + str.substring(1);
    }
	public static void main(String[] args) throws Exception {
		System.out.println(getJavaName("itemId"));
		System.out.println(getJavaName("itemid"));
		System.out.println(getJavaName("item_id"));
		System.out.println(getJavaName("item_Id"));
		System.out.println(getJavaName("e_Id"));
		System.out.println(getJavaName("e_id"));
	}
}
