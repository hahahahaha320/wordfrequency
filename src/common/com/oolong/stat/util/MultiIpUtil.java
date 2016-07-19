package com.oolong.stat.util;

import org.apache.commons.lang.StringUtils;

public class MultiIpUtil {
	public static String getRealIp(String... ips)	{
		for(String ip : ips)	{
			if(StringUtils.isNotEmpty(ip) && ip.length() > 5)	{
				return ip;
			}
		}
		return "";
	}
	public static void main(String[] args) {
		System.out.println(getRealIp("","12.2","123.123.12.2","192.168.1.2"));
		System.out.println(getRealIp("","12.2"));
	}
}
