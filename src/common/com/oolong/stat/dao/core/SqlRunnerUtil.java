package com.oolong.stat.dao.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SqlRunner;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oolong.stat.util.LogUtil;

/**
 * 对SqlRunner的包装,不处理异常，并关闭连接,尽量不用
 * @author xfinal
 *
 */
public class SqlRunnerUtil {
	
	private static Logger log = LoggerFactory.getLogger(SqlRunnerUtil.class);
	
	public static Map<String,Object> selectOne(String sql,Object... obj)	{
		SqlSession session = MybatisUtil.openSession();  
		SqlRunner runner = new SqlRunner(session.getConnection());
		try	{
			return runner.selectOne(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError(log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}
	public static List<Map<String,Object>> selectAll(String sql,Object... obj)	{
		SqlSession session = MybatisUtil.openSession();  
		SqlRunner runner = new SqlRunner(session.getConnection());
		try	{
			return runner.selectAll(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError(log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}
	public static Object insert(String sql,Object... obj)	{
		SqlSession session = MybatisUtil.openSession();  
		SqlRunner runner = new SqlRunner(session.getConnection());
		try	{
			return runner.insert(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError(log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}
	public static Object update(String sql,Object... obj)	{
		SqlSession session = MybatisUtil.openSession();  
		SqlRunner runner = new SqlRunner(session.getConnection());
		try	{
			return runner.update(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError(log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}
	public static void main(String[] args) {
		Date start = new Date();
		for(int i=0;i<20000;i++)	{
			SqlSessionUtil.selectOne("gh.getGhidNeedCalLatest", "gh_8fd263f3c251");
		}
		Date end = new Date();
		
		long interval = end.getTime()-start.getTime();
		System.out.println("time:"+interval);
	}
}
