package com.oolong.stat.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigManager	{
	
	private static Logger log = LoggerFactory.getLogger(ConfigManager.class);

	private final static String configFile = "config.properties";
	private static boolean checkLoad = false;
	
	private static Properties properties;
	private static Map<Object,Object> propertiesMap = new HashMap<Object,Object>();
	private static long configFileLastModifyTime = 0L;

	static {
	    init();
	}

	private static void init() {
		properties = new Properties();
		InputStream is = null;
		String[] strs = configFile.split(",");
		for(String str : strs) {
    		try {
    			//is = Thread.currentThread().getContextClassLoader().getResourceAsStream(str);
    			is = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource(str).getFile());

    			properties.load(is);
    			propertiesMap.putAll(properties);
    		} catch (Exception e) {
    			LogUtil.logError("Get property file Error,configFile="
    					+ configFile + "!", log,e);
    		} finally {
    			try {
    				if (is != null)
    					is.close();
    			} catch (IOException exception) {
    			    
    			}
    		}
		}
	}

	public static void reset() {
		init();
	}

	/**
	 * Retun a value for certain key.
	 * 
	 * @param key
	 *            a certain key define in properties file.
	 * @return value
	 */
	public static String getValue(String key) {
		checkReload();
		return properties.getProperty(key);
	}
	public static int getInt(String key)	{
		return Integer.parseInt(getValue(key));
	}
	public static int getInt(String key,int def)	{
		try	{
			return Integer.parseInt(getValue(key));
		} catch(Exception e)	{
			e.printStackTrace();
			return def;
		}
	}
	/**
	 * 获取key对应的value，如果value为null，则返回defaultValue
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String key, String defaultValue) {
		checkReload();
		String value = properties.getProperty(key);
		if (value == null)
			value = defaultValue;
		return value;
	}

	/**
	 * 获取boolean类型的属性值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getValue(String key, boolean defaultValue) {
		String str = getValue(key, "false");
		boolean value = str.equalsIgnoreCase("true");
		return value;
	}

	/**
	 * 检查配置文件是否更新过
	 */
    @SuppressWarnings("unused")
	private static void checkLoad() {
    	if(!checkLoad)	{
    		return;
    	}
	    String[] strs = configFile.split(",");
	    long maxLastModifyTime = 0;
        for(String str : strs) {
    		try {
    			URL configFileUrl = Thread.currentThread().getContextClassLoader().getResource(str);
    			File cf = new File(configFileUrl.toString());
    			if (maxLastModifyTime < cf.lastModified()) {
    			    maxLastModifyTime = cf.lastModified();
    			}
    		} catch (Exception e) {
    			LogUtil.logError(log,e);
    		}
        }
        if (configFileLastModifyTime < maxLastModifyTime) {
            init();
            configFileLastModifyTime = maxLastModifyTime;
        }
		if (properties == null) {
			init();
		}
	}
    /*
     * 每间隔 reloadInterval 重新载入配置文件 
     */
    private static final int reloadInterval = 5*60*1000;//5分钟
    private static long nextReloadTime = 0;
    private static void checkReload() {
	    long now = new Date().getTime();
	    if(nextReloadTime < now)	{
	    	reset();
	    	nextReloadTime = now + reloadInterval;
	    }
	    if (properties == null) {
	    	reset();
		}
    }
    

	/**
	 * 获取属性值的Map
	 * 
	 * @return
	 */
	public static Map<Object,Object> getPropertiesMap() {
		checkReload();
		return propertiesMap;
	}
	
	
	
}
