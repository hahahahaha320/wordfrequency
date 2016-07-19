package com.oolong.stat.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {
	
	private static Logger log = LoggerFactory.getLogger(LogUtil.class);
	
	public static String getExceptionStackTrace(Throwable e)	{
		
    	ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); 
		PrintStream printer = new PrintStream(byteOut);
    	e.printStackTrace(printer);
    	try {
    		printer.flush();
			byteOut.flush();
			return byteOut.toString();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally	{
			try {
				byteOut.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				printer.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
    	return "";
    }
	
	public static String getShortExceptionStackTrace(Throwable e)	{
    	String stackStr = getExceptionStackTrace(e);
    	String[] sa = stackStr.split("\n");
    	int length = sa.length>6?6:sa.length;
    	String result = "";
    	for(int i=0;i<length;i++)	{
    		result = result + sa[i] + "\n";
    	}
    	return result;
    }
	
	public static void logError(Logger log,Throwable e)	{
		log.error(getExceptionStackTrace(e));
	}
	public static void logError(String title,Logger log,Throwable e)	{
		log.error(title);
		log.error(getExceptionStackTrace(e));
	}
	
	public static void logError(Throwable e)	{
		log.error(getExceptionStackTrace(e));
	}
	public static void logError(String title,Throwable e)	{
		log.error(title);
		log.error(getExceptionStackTrace(e));
	}
	
	public static void logShortError(Logger log,Throwable e)	{
		log.error(getShortExceptionStackTrace(e));
	}
	public static void logShortError(String title,Logger log,Throwable e)	{
		log.error(title);
		log.error(getShortExceptionStackTrace(e));
	}
	public static void logShortError(Throwable e)	{
		log.error(getShortExceptionStackTrace(e));
	}
	public static void logShortError(String title,Throwable e)	{
		log.error(title);
		log.error(getShortExceptionStackTrace(e));
	}
}
