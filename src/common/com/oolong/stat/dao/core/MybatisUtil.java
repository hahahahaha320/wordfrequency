package com.oolong.stat.dao.core;

import java.io.IOException;  
import java.io.Reader;  
import java.sql.Connection;
  


import org.apache.ibatis.io.Resources;  
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;  
import org.apache.ibatis.session.SqlSessionFactoryBuilder;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oolong.stat.util.LogUtil;

  
public class MybatisUtil {  
      
    private static SqlSessionFactory sqlSessionFactory = null;  
    
    private static Logger log = LoggerFactory.getLogger(MybatisUtil.class);
    
    static {  
        //String resource = "mybatis.xml";
        String resource = "mybatis_druid.xml";
        Reader reader = null;  
        try {  
            reader = Resources.getResourceAsReader(resource);  
        } catch (IOException e) {  
            LogUtil.logError("While load mybatis.xml error:", log, e);
        }         
        
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);  
    }  
      
    public static SqlSessionFactory getSqlSessionFactory(){  
        return sqlSessionFactory;  
    }  
    public static SqlSession openSession(){  
        return sqlSessionFactory.openSession(true);  
    }  
    public static Connection getConnection(){  
        return sqlSessionFactory.openSession(true).getConnection();  
    }  
}  