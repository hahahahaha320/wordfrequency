package com.oolong.stat.dao.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oolong.stat.util.LogUtil;

//一些工具方法：
public class SqlUtil {
	
	private static Logger log = LoggerFactory.getLogger(SqlUtil.class);
	private static Logger errSqlLog = LoggerFactory.getLogger("errSqlLog");
	
    //update数据，如果update数为0，则先insert，然后update.保证至少更新一条数据
    public static int updateOne(String updateSql,String insertSql,Object param)	{
		return updateOne(updateSql,insertSql,param,param);
    }
    public static int updateOne(String updateSql,String insertSql,Object updateParam,Object insertParam)	{
    	int num = SqlSessionUtil.update(updateSql,updateParam);
		if(num == 0)	{
			synchronized(SqlUtil.class)	{
				num = SqlSessionUtil.update(updateSql,updateParam);
				if(num != 0)	{
					return num;
				}
				SqlSessionUtil.insert(insertSql,insertParam);
				num = SqlSessionUtil.update(updateSql,updateParam);
			}
		}
		return num;
    }
    
    public static boolean isRowExist(String selectSql,Object param)	{
    	Object obj = SqlSessionUtil.selectOne(selectSql,param);
		if(obj == null)	{
			return false;
		} else {
			return true;
		}
    }
    
    public static Integer getResultInt(String selectSql,Object param)	{
    	Object obj = SqlSessionUtil.selectOne(selectSql,param);
    	if(obj == null)	{
			return 0;
		} else {
			return (Integer)obj;
		}
    }
    public static Long getResultLong(String selectSql,Object param)	{
    	Object obj = SqlSessionUtil.selectOne(selectSql,param);
    	if(obj == null)	{
			return 0L;
		} else {
			return (Long)obj;
		}
    }
    
    public static int saveBatchToDbSafely(String batchInsertSql,List<?> batchList)	{
		try	{
			return SqlSessionUtil.insert(batchInsertSql, batchList);
		} catch(Exception e)	{
			LogUtil.logShortError("save db batch error.",log,e);
			return saveToDbOneByOne(batchInsertSql, batchList);
		}
	}
	
    private static int saveToDbOneByOne(String batchInsertSql,List<?> batchList)	{
		List<Object> list = new ArrayList<Object>();
		int sucNum = 0;
		for(Object obj : batchList)	{
			try	{
				list.add(obj);
				SqlSessionUtil.insert(batchInsertSql,list);
				sucNum++;
			} catch(Exception e)	{
				SqlSession session = MybatisUtil.openSession();
				String sql = SqlHelper.getNamespaceSql(session,batchInsertSql,list);
				errSqlLog.info("error sql:\n"+sql + "\n");
			}
			list.clear();
		}
		return sucNum;
	}
}
