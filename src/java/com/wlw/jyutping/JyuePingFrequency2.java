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

public class JyuePingFrequency2 {
	
	private static final String loaclFileCharSet = "GBK";
	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws Exception {
		String path = "G:/粤语文字/粤语经典对白歌词";
//		String path = "G:/粤语文字/电影字幕";
		File dir = new File(path);
		File[] files = dir.listFiles();
		
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
				String pre = "";
				for(String word : words)	{
					if(word==null || word.length() ==0)	{
						continue;
					}
					if(isChinese(word.charAt(0))){
						//System.out.print(word);
						String twoWord = pre+word;
						Integer num = result.get(twoWord);
						if(num == null)	{
							num = 0;
						}
						result.put(twoWord,++num);
						pre = word;
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
	}
	public static void printResult(Map<String,Integer> result)	{
		Iterator<Entry<String, Integer>> iter = result.entrySet().iterator(); 
		int index=0;
		while (iter.hasNext()) { 
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next(); 
			if(entry.getValue() > 5 && entry.getKey().length() > 1)	{
				index++;
				System.out.print(index+"."+entry.getKey() +"[" + entry.getValue() + "]	");
				if(index%5 == 0)	{
					System.out.println("\n");
				}
			}
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
		trimMap.put('～',1);trimMap.put('－',1);
		
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
