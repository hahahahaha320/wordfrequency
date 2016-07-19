package com.wlw.jyutping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.oolong.stat.dao.core.SqlSessionUtil;
import com.spreada.utils.chinese.ZHConverter;

public class JyuePingFrequency {
	
	private static final String loaclFileCharSet = "GBK";
	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws Exception {
//		String path = "G:/粤语文字/粤语经典对白歌词";
		String path = "G:/粤语文字/all_text";
		File dir = new File(path);
		File[] files = dir.listFiles();
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
		
		Map<String,Integer> result = new HashMap<String,Integer>();
		for(File file : files)	{
			if(file.isDirectory())	{
				continue;
			}
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()),loaclFileCharSet));  
			String line = null;
			//System.out.println(file.getName());
			while((line=br.readLine()) != null)	{
				String[] words = line.split("");
				for(String word : words)	{
					if(word==null || word.length() ==0)	{
						continue;
					}
					if(isChinese(word.charAt(0))){
						//System.out.print(word);
						word = converter.convert(word);
						Integer num = result.get(word);
						if(num == null)	{
							num = 0;
						}
						result.put(word,++num);
					}
				}
			}
			//System.out.println();
		}
//		for(String key : result.keySet())	{
//			System.out.println(key +" : " + result.get(key));
//		}
		result = sortMap(result);
		//System.out.println(result.size());
		
		//statResult(result);
		
		printResult(result);
		//saveResult(result);
	}
	
	public static void printResult(Map<String,Integer> result)	{
		Iterator<Entry<String, Integer>> iter = result.entrySet().iterator(); 
		int index=0;
		while (iter.hasNext()) { 
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next(); 
			index++;
			System.out.print(index+"."+entry.getKey() +"[" + entry.getValue() + "]	");
			if(index%5 == 0)	{
				System.out.println("\n");
			}
		} 
	}
	public static void saveResult(Map<String,Integer> result)	{
		Iterator<Entry<String, Integer>> iter = result.entrySet().iterator(); 
		while (iter.hasNext()) { 
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next(); 
			
			Jyutping ping = new Jyutping();
			ping.setWord(entry.getKey());
			ping.setTimes(entry.getValue());
			ping.setSrc("movie");
			
			SqlSessionUtil.insert("jyutping.insert",ping);
		} 
	}
	
	public static void statResult(Map<String,Integer> result)	{
		int total = 0;
		for(String key : result.keySet())	{
			int count = result.get(key);
			total += count;
		}
		for(int i=1;i<20;i++)	{
			int totalGreate = 0;
			int wordNum = 0;
			for(String key : result.keySet())	{
				int count = result.get(key);
				if(count >= i)	{
					totalGreate += count;
					wordNum++;
				}
			}
			String str = String.format("%d次以上,共%d字，占比%.2f%%,出现%d次,占比%.2f%%",i,wordNum,(wordNum+0f)/result.size()*100,totalGreate,(totalGreate+0f)/total*100);
			System.out.println(str);
		}
		
	}
	
	private static Map<Character,Integer> trimMap = new HashMap<Character,Integer>();
	static {
		trimMap.put('，',1);trimMap.put('（',1);trimMap.put('）',1);
		trimMap.put('？',1);trimMap.put('：',1);trimMap.put('！',1);
		trimMap.put('～',1);trimMap.put('Ｏ',1);trimMap.put('Ｋ',1);
		trimMap.put('Ｄ',1);
		
	}
	public static boolean isChinese(char a) { 
	    if(trimMap.containsKey(a))	{
	    	return false;
	    }
		int v = (int)a; 
	    return (v >=19968 && v <= 171941); 
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map sortMap(Map oldMap) {  
        ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldMap.entrySet());  
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {  
            public int compare(Entry<java.lang.String, Integer> arg0,  
                    Entry<java.lang.String, Integer> arg1) {  
                return arg1.getValue() - arg0.getValue();  
            }  
        });  
        Map newMap = new LinkedHashMap();  
        for (int i = 0; i < list.size(); i++) {  
            newMap.put(list.get(i).getKey(), list.get(i).getValue());  
        }  
        return newMap;  
    }  
}
