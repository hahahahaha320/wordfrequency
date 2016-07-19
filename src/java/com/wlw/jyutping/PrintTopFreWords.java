package com.wlw.jyutping;

import java.util.List;

import com.oolong.stat.dao.core.SqlSessionUtil;

public class PrintTopFreWords {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List<Jyutping> words = SqlSessionUtil.selectList("jyutping.getTopFreWord");
		System.out.println("\n\n\n\n");
		for(int i=1;i<=words.size();i++)	{
			Jyutping word = words.get(i-1);
			System.out.print((word.getId())+"."+word.getWord() +"[" + word.getGoodPingyin() + "]");
			if(i%5 == 0)	{
				System.out.println("\n");
			} 
			else {
				System.out.print("\t");
			}
		}

	}
}
