package com.wlw.jyutping;

import java.util.List;

import com.oolong.stat.dao.core.SqlSessionUtil;

public class HanyuPing {
	public static void main(String[] args) throws Exception {
		getPingyin();
	}
	@SuppressWarnings({ "unchecked" })
	public static void getPingyin() throws Exception {
		
		List<String> list = SqlSessionUtil.selectList("jyutping.getAllJyutping");
		
		for(String word : list)	{
			String[] pingyins = PinYinUtil.getPinYins(word.charAt(0));
			
			Jyutping ping = new Jyutping();
			ping.setWord(word);
			try	{
				ping.setHanyuPingyin(pingyins[0].substring(0, pingyins[0].length()-1));
				ping.setHanyuTone(Integer.parseInt(pingyins[0].substring(pingyins[0].length()-1)));
				ping.setHanyuPingyinFull(pingyins[0]);
				ping.setHanyuPingyinFulls(list2Str(pingyins));
				
				SqlSessionUtil.update("jyutping.updateHanyuPingByWord", ping);
			} catch(Exception e)	{
				System.out.println("Error:"+word+",pingyins="+pingyins);
				e.printStackTrace();
			}
		}
			
		System.out.println("over...");
	}
	private static String list2Str(String[] list)	{
		String result = "";
		for(String str : list)	{
			result = result + str + ",";
		}
		return result;
	}
}
