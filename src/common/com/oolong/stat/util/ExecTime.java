package com.oolong.stat.util;

import java.util.Date;

import lombok.Data;

public @Data class ExecTime {
	private String name;
	private long execNum = 0;
	private long execTime = 0;
	
	private Date start;
	private Date end;
	
	public ExecTime()	{
		this.start = new Date();
	}
	public ExecTime(String name)	{
		this.name = name;
		this.start = new Date();
	} 
	
	public float getAverTime()	{
		return (execTime+0f) / execNum;
	}
	
	public float getTotalTimeSec()	{
		return (execTime+0f)/1000;
	}
	public float getTotalTimeMin()	{
		return (execTime+0f)/1000/60;
	}
	
	public void start()	{
		this.start = new Date();
	}
	public void end()	{
		this.end(1);
	}
	public void end(int addExecNum)	{
		this.end =new Date();
		execNum = execNum+addExecNum;
		execTime = execTime+(end.getTime()-start.getTime());
	}
	
	public String info()	{
		String format = "Task [%s],total num:%d,total time:%fSec(%fMin),aver time:%fms"; 
		return String.format(format,name,execNum,getTotalTimeSec(),getTotalTimeMin(),getAverTime());
	}
}
