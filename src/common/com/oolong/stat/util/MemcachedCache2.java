package com.oolong.stat.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
/**
 * 以静态方法的方式访问:MemcachedCache2.put()...，无法自动类型转化
 * @author xfinal
 *
 */
public class MemcachedCache2  {

    private static Logger log = LoggerFactory.getLogger(MemcachedCache2.class);
    
	private static MemCachedClient memCachedClient;
	private static SockIOPool pool;
	
	
	
	static {
		init();
	}
	public static void flushAll() {
		if(memCachedClient != null)	{
			memCachedClient.flushAll();
		}
	}
	/**
	 * <pre>
	 * <h3>初始化memcached pool</h3>
	 * </pre>
	 */
	private static synchronized void init() {
		if (pool == null) {
			Properties propertie = new Properties();
			InputStream is = null;
			try {
				is = MemcachedCache2.class.getResourceAsStream("/memcached_cfg.properties");
				propertie.load(is);
				log.info("初始化MemCached配置文件成功!");

				pool = SockIOPool.getInstance();
				pool.setServers(propertie.getProperty("MemCached.Servers")
						.split(","));
				pool.setFailover(Boolean.parseBoolean(propertie
						.getProperty("MemCached.Failover")));
				pool.setInitConn(Integer.parseInt(propertie
						.getProperty("MemCached.InitConn")));
				pool.setMinConn(Integer.parseInt(propertie
						.getProperty("MemCached.MinConn")));
				pool.setMaxConn(Integer.parseInt(propertie
						.getProperty("MemCached.MaxConn")));
				pool.setMaintSleep(Long.parseLong(propertie
						.getProperty("MemCached.MaintSleep")));
				pool.setNagle(Boolean.parseBoolean(propertie
						.getProperty("MemCached.Nagle")));
				pool.setSocketTO(Integer.parseInt(propertie
						.getProperty("MemCached.SocketTO")));
				pool.setAliveCheck(Boolean.parseBoolean(propertie
						.getProperty("MemCached.AliveCheck")));
				pool.initialize();

				if (memCachedClient == null) {
					memCachedClient = new MemCachedClient();
				}
			} catch (Exception e) {
				LogUtil.logError("Memcached initial SockIOPool error.",log, e);
			} finally {
				if (is != null)
					try {
						is.close();
					} catch (IOException e) {
						LogUtil.logError(log, e);
					}
			}
			log.info("初始化Memcached成功!");
		}
	}
	//受 Consts.cache_open控制，决定启不启用缓存
	public static Object reflectGet(String key, Object instance, String methodName,
			Class<?>[] paramType, Object... args) {
		return reflectGet(key, 0, instance, methodName, paramType, args);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object reflectGet(String key,int minutes,Object instance, String methodName,
			Class<?>[] paramType, Object... args) {
		try {
			Object cachedVal = get(key,minutes);
			if (cachedVal == null) {
				Method method;
				if(instance instanceof Class)	{
					method = ((Class)instance).getMethod(methodName, paramType);
				} else {
					method = instance.getClass().getMethod(methodName, paramType);
				}
				cachedVal = method.invoke(instance, args);
				if(minutes == 0 )	{
					put(key, cachedVal);
				} else {
					put(key, cachedVal,minutes);
				}
			}
			return cachedVal;
		} catch (Exception e) {
			LogUtil.logError("Memcache reflectGet error.",log, e);
		} 
		return null;
	}
	public static Object get(String key) {
		try	{
			return  memCachedClient.get(key);
		} catch(Exception e)	{
			LogUtil.logError("While Memcached.get error:",log, e);
		}
		return null;
	}
	public static Object get(String key,Integer minutes) {
		try	{
			return memCachedClient.get(key);
		} catch(Exception e)	{
			LogUtil.logError("While Memcached.get error:",log, e);
		}
		return null;
	}
	public static boolean put(String key, Object value) {
		if(value == null)	{
			memCachedClient.delete(key);
			return true;
		}
		return memCachedClient.set(key, value);
	}
	//如果put的对象为null,则删除掉
	public static boolean put(String key, Object value,Integer minutes) {
		if(value == null)	{
			memCachedClient.delete(key);
			return true;
		}
		if(minutes < 0)	{
			return false;
		}
		Date expriry = DateUtils.addMinutes(new Date(), minutes);
		return memCachedClient.set(key, value,expriry);
	}
	public static boolean delete(String key) {
		return memCachedClient.delete(key);
	}
	
	//检查key是否存在，若不存在，则放入cache
	public static boolean isKeyExist(String key,Integer minutes)	{
		if(get(key) == null)	{
			put(key,"1", minutes);
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		String key = "1statjs_stat_page_hour_wap9e81fd59adb0f14foib375a3a6aad_http://hd.playwx.com/bkrcvt082soeblzg/scr?token_2014-07-24_0_dodei7t8mt7iayg8hgeplm5o";
//		String key = "1statjs_stat_page";
		
		key = MD5.to_MD5(key);
		
		Date start = new Date();
		for(int i=0;i<3;i++)	{
			System.out.println(MemcachedCache2.isKeyExist(key,60));
			//get(key, 60);
		}
		Date end = new Date();
		
		System.out.println((end.getTime()-start.getTime()));
		
	}
}
