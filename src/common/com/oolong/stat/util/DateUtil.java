package com.oolong.stat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@SuppressWarnings("deprecation")
public class DateUtil {
    private static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static String DEFAULT_SHORT_FORMAT = "yyyy-MM-dd";
    private static SimpleDateFormat DEFAULT_SIMPLE_DATE_FORMAT = new SimpleDateFormat(DEFAULT_FORMAT);
    private static SimpleDateFormat DEFAULT_SHORT_FORMAT_DATE_FORMAT = new SimpleDateFormat(DEFAULT_SHORT_FORMAT);
    private static final Log LOG = LogFactory.getLog(DateUtil.class.getName());
    
    //Convert Date to String
    public static String dateToString(Date date) {
        if(date == null)    {
            return null;
        }
        return DEFAULT_SIMPLE_DATE_FORMAT.format(date);
    }
    public static String dateToStringShort(Date date) {
        if(date == null)    {
            return null;
        }
        return DEFAULT_SHORT_FORMAT_DATE_FORMAT.format(date);
    }
    public static String dateToString(Date date,String format) {
        if(StringUtil.isEmpty(format))    {
            return dateToString(date);
        }
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        return simpleFormat.format(date);
    }
    //Convert String to Date
    public static Date stringToDate(String str)   {
        try {   
            return DEFAULT_SIMPLE_DATE_FORMAT.parse(str);
        } catch(ParseException e)   {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static Date stringToDateShort(String str)   {
        try {   
            return DEFAULT_SHORT_FORMAT_DATE_FORMAT.parse(str);
        } catch(ParseException e)   {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public static Date stringToDate(String str,String format)   {
        if(StringUtil.isEmpty(format))    {
            return stringToDate(str);
        }
        try {   
            SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
            return simpleFormat.parse(str);
        } catch(ParseException e)   {
            LOG.error(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    //add amount to date
    public static Date addYear(Date date,int amount)    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR,amount);
        return c.getTime();
    }
    public static Date addMonth(Date date,int amount)    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH,amount);
        return c.getTime();
    }
    public static Date addDay(Date date,int amount)    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,amount);
        return c.getTime();
    }
    public static Date addHour(Date date,int amount)    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR_OF_DAY,amount);
        return c.getTime();
    }
    public static Date addMinute(Date date,int amount)    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE,amount);
        return c.getTime();
    }
    public static Date addSecond(Date date,int amount)    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND,amount);
        return c.getTime();
    }
    //返回当前星期几,从1-7
    public static int getCurrWeek()	{
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if(week==0)	{
			week = 7;
		}
		return week;
	}
    
	public static int daysBetween(String smdate, String bdate) {
		try	{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days = (time2 - time1) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch(Exception e)	{
			return 0;
		}
	}
	public static String getYesterdayStr()	{
		return getDateStr(-1);
	}
    public static String getTodayStr()	{
    	return getDateStr(0);
	}
    public static String getTomorrowStr()	{
    	return getDateStr(1);
	}
    public static String getDateStr(int interval)	{
    	Date now = new Date();
		Date yesterday = addDay(now,interval);
		String str = dateToString(yesterday, "yyyy-MM-dd");
		return str;
    }
    
    public static Integer getHourIndex(String tm)	{
		if(tm == null || tm.length() < 13)	{
			return -1;
		}
    	String hour = tm.substring(11,13);
		return Integer.parseInt(hour);
	}
    
	public static Integer getHourIndex()	{
		Date now = new Date();
		return now.getHours();
	}
    public static Integer getMinuteIndex()	{
		Date now = new Date();
		return now.getMinutes();
	}
    
    public static Date getRecentDateFixHour(Integer hour,Integer minute)	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND,0);
		if(cal.before(Calendar.getInstance()))	{
			cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH)+1);
		}
		return cal.getTime();
	}
    public static Date getRecentDateFixHour(Integer hour,Integer minute,Integer second)	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND,second);
		if(cal.before(Calendar.getInstance()))	{
			cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH)+1);
		}
		return cal.getTime();
	}
    public static Date getRecentDateFixMinute(Integer minute)	{
		return getRecentDateFixMinute(minute,0);
	}
    public static Date getRecentDateFixMinute(Integer minute,Integer second)	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND,second);
		if(cal.before(Calendar.getInstance()))	{
			cal.set(Calendar.HOUR_OF_DAY,cal.get(Calendar.HOUR_OF_DAY)+1);
		}
		return cal.getTime();
	}
    public static Date getRecentDateFixSecond(Integer second)	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND,second);
		if(cal.before(Calendar.getInstance()))	{
			cal.set(Calendar.MINUTE,cal.get(Calendar.MINUTE)+1);
		}
		return cal.getTime();
	}
    
    public static long millsBetween(Date date1,Date date2)	{
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		long between_mills = time2 - time1;
		return between_mills;
    	
    }
    public static long millsFromNow(Date future)	{
		long time1 = (new Date()).getTime();
		long time2 = future.getTime();
		long between_mills = time2 - time1;
		return between_mills;
    	
    }
    
    public static void main(String[] args) {
		System.out.println(getRecentDateFixSecond(10));
	}
}
