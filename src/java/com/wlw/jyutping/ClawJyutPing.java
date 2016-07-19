package com.wlw.jyutping;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oolong.stat.dao.core.SqlSessionUtil;
import com.oolong.stat.util.MapUtil;
import com.spreada.utils.chinese.ZHConverter;

public class ClawJyutPing {
	public static void main(String[] args) throws Exception {
		
//		SqlSessionUtil.update("jyutping.resetErrorToNull");
//		getPingyin(15);
//		
//		SqlSessionUtil.update("jyutping.resetErrorToNull");
//		getPingyin(3);
		
		SqlSessionUtil.update("jyutping.resetErrorToNull");
		getPingyin(1);
//		test();
	}
	@SuppressWarnings({ "unchecked" })
	public static void getPingyin(int wordNum) throws Exception {
		
		List<String> list;
		int totalWord = 0;
		
		while((list = SqlSessionUtil.selectList("jyutping.getJyutping",wordNum)).size() > 0)	{
			String words = "";
			for(String str : list)	{
				words = words+str;
			}
			if(wordNum == 1)	{
				words = words+"讕";
			}
			
			String baseUrl = "http://www.yueyv.cn/?keyword=";//%CE%D2
			String url = baseUrl + URLEncoder.encode(words, "GBK");
			System.out.println("Going to get words:"+words);
			Document doc=  getDocumentFormUrl(url);
			if(doc == null)	{
				SqlSessionUtil.update("jyutping.updateError", words);
				continue;
			}
			
			
			Elements eles = doc.select(".search-result h2");
			if(eles.size() != words.length())	{
				System.out.println("出错："+words+",eles.size()="+eles.size());
				SqlSessionUtil.update("jyutping.updateError", words);
				continue;
			}
			
			for(int i=0;i<eles.size();i++)	{
				String word = words.charAt(i)+"";
				Element ele = eles.get(i);
				
				/*
				String word = ele.select("a span").get(0).text();
				if(!oriWord.equals(word))	{
					word = new String(word.getBytes("gb2312"),"UTF-8");
					System.out.println(ele.select("a span").get(0));
					if(!oriWord.equals(word))	{
						SqlSessionUtil.update("jyutping.updateError",oriWord);
						continue;
					}
					
				}
				*/
				
				Elements values = ele.select("select option");
				String pinyinAll = "";
				for(Element value : values)	{
					pinyinAll = pinyinAll+value.val()+",";
				}
				String goodPingyin = pinyinAll.substring(0,pinyinAll.indexOf(",")).trim();
				String pingyin = "";
				String tone = "";
				try	{
					pingyin = goodPingyin.substring(0,goodPingyin.length()-1);
					tone = goodPingyin.substring(goodPingyin.length()-1,goodPingyin.length());
				} catch(Exception e)	{
					SqlSessionUtil.update("jyutping.updateError", word);
					System.out.println("Error:word:"+word+",pinyinAll:"+ pinyinAll+",goodPingyin:"+ goodPingyin);
					continue;
				}
				Map<String, Object> paraMap = MapUtil.getMap("allPingyin", pinyinAll, "word", word);
				paraMap.put("goodPingyin", goodPingyin);
				paraMap.put("pingyin", pingyin);
				paraMap.put("tone", Integer.parseInt(tone));
				SqlSessionUtil.update("jyutping.updateByWord", paraMap);
				totalWord++;
			}
			System.out.println("完成 " + totalWord+" 字...");
		}
		System.out.println("over...");
		
	}
	public static Document getDocumentFormUrl(String url)	{
		for(int i=0;i<5;i++)	{
			try	{
				Document doc =  Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0").get();
				if(doc != null)	{
					return doc;
				}
			} catch(HttpStatusException e1)	{
				e1.printStackTrace();
				return null;
			} catch(Exception e)	{
				e.printStackTrace();
			} 
		}
		return null;
	}
	public static void test() {
		ZHConverter converter = ZHConverter.getInstance(ZHConverter.SIMPLIFIED);
		String simplifiedStr = converter.convert("有背光的機械式鍵盤華");
		System.out.println(simplifiedStr);
		
		ZHConverter.convert("有背光的机械式键盘", ZHConverter.TRADITIONAL);
	}
}
