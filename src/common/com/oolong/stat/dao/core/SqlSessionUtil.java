package com.oolong.stat.dao.core;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SqlRunner;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oolong.stat.util.LogUtil;

/**
 * 对SqlSession的包装,不处理异常，并关闭连接
 * @author stanley
 *
 *注意：此工具做了特殊处理，在update和insert操作时，会把 sql_mode 设置为空，使得如果字段太长可以自动截取
 *
 */
@SuppressWarnings("rawtypes")
public class SqlSessionUtil {
	
	
	private static Logger log = LoggerFactory.getLogger(SqlSessionUtil.class);
	
	public static Object selectOne(String sql)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			return session.selectOne(sql);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis selectOne error,startTime="+startTime+",sql="+sql,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

	public static Object selectOne(String sql,Object obj)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			return session.selectOne(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis selectOne error,startTime="+startTime+",sql="+sql+",param="+obj,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

	public static List selectList(String sql)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			return session.selectList(sql);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis selectList error,startTime="+startTime+",sql="+sql,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}
	public static List selectList(String sql, Object obj)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			return session.selectList(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis selectList error,startTime="+startTime+",sql="+sql+",param="+obj,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

	public static Map selectMap(String sql, String str)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			return session.selectMap(sql,str);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis selectMap error,startTime="+startTime+",sql="+sql+",param="+str,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}
	public static Map selectMap(String sql, Object obj, String str)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			return session.selectMap(sql,obj,str);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis selectMap error,startTime="+startTime+",sql="+sql+",obj="+obj+",str="+str,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

	public static int insert(String sql)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			resetSqlMode(session,"");
			return session.insert(sql);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis insert error,startTime="+startTime+",sql="+sql,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

	public static int insert(String sql, Object obj)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			resetSqlMode(session,"");
			return session.insert(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis insert error,startTime="+startTime+",sql="+sql+",param="+obj,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

	public static int update(String sql)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			resetSqlMode(session,"");
			return session.update(sql);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis update error,startTime="+startTime+",sql="+sql,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

	public static int update(String sql, Object obj)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			resetSqlMode(session,"");
			return session.update(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis update error,startTime="+startTime+",sql="+sql+",param="+obj,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}
	
	//带设置sql_mode的update,insert
	private static void resetSqlMode(SqlSession session,String sqlMode) throws SQLException{
		String setSql = "SET SESSION sql_mode='"+sqlMode+"'";
		SqlRunner runner = new SqlRunner(session.getConnection());
		runner.update(setSql);
	}
	
	public static int delete(String sql)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			return session.delete(sql);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis delete error,startTime="+startTime+",sql="+sql,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

	public static int delete(String sql, Object obj)	{
		Date startTime = new Date();
		SqlSession session = MybatisUtil.openSession();  
		try	{
			return session.delete(sql,obj);
		} catch(Exception e)	{
			LogUtil.logError("Mybatis delete error,startTime="+startTime+",sql="+sql+",param="+obj,log, e);
	    	throw new RuntimeException(e);
	    } finally	{
	    	session.close();
	    }
	}

}
