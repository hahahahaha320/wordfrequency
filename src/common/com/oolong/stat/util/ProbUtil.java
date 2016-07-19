package com.oolong.stat.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProbUtil {
	
	private static Logger log = LoggerFactory.getLogger(ProbUtil.class);
	
	public static Map<Integer,ProbBean> precentMap 
		= new HashMap<Integer,ProbBean>();
	
	//以  prob 的概率产生true
	public static boolean getProbBool(int prob)	{
		
		if(prob == 0)	{
			return false;
		} 
		if(prob == 100)	{
			return true;
		}
		ProbBean bean = precentMap.get(prob);
		if(bean == null)	{
			bean = new ProbBean();
			precentMap.put(prob, bean);
		}
		log.info("Current target prob:" + prob + ",ProbBean :" + bean);
		float curProb = bean.getPercent()*100;
		bean.addTotal();
		if(curProb < prob)	{
			bean.addValidNum();
			return true;
		} else if(curProb > prob)	{
			return false;
		} else if(curProb == prob)	{
			Date now = new Date();
			boolean result = (now.getTime()/2) ==1;
			if(result)	{
				bean.addValidNum();
			}
			return result;
		}
		return true;
	}
	/*
	public static boolean getProbBool2(int prob)	{
		if((Math.random()*100-prob)<0){
			return true;
		}
		return false;
	}
	*/
	static class ProbBean	{
		int total = 0;
		int validNum = 0;
		public float getPercent()	{
			if(total == 0)	{
				return 0;
			}
			return (0F+validNum)/total;
		}
		public void addTotal()	{
			total++;
			if(total > 20000)	{
				total = 0;
				validNum = 0;
			}
		}
		public void addValidNum()	{
			validNum++;
		}
		public String toString()	{
			return String.format("total=%d,validNum=%d,percent=%.2f", total,validNum,getPercent());
		}
	}
	
	//取得 1~total之间的随机整数
	public static int getRandom(int total)	{
		int random = new Random().nextInt(total) + 1;
		return random;
	}
	public static boolean getProbBool2(int prob)	{
		if(prob == 0)	{
			return false;
		} 
		if(prob == 100)	{
			return true;
		}
		int num = getRandom(10000);
		if(num <= prob*100)	{
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		for(int j=0;j<100;j++)	{
			int trueNum = 0;
			int totalNum = 100;
			for(int i=0;i<totalNum;i++)	{
				boolean result = getProbBool(90);
				if(result)	{
					trueNum++;
					//System.out.print(1);
				} else {
					//System.out.print(0);
				}
			}
			//System.out.println();
			//System.out.println("totalNum:" + totalNum);
			System.out.println("trueNum:" + trueNum);
		}
	}
}