package com.oolong.stat.dao.core;

import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.datasource.DataSourceFactory;
import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceFactory implements DataSourceFactory {

	protected DruidDataSource dataSource;
	 
    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }
 
    public DataSource getDataSource() {
        return this.dataSource;
    }
 
    public void setProperties(Properties properties) {
    	
    	dataSource.setName(properties.getProperty("name"));
    	dataSource.setUrl(properties.getProperty("url"));
    	dataSource.setUsername(properties.getProperty("username"));
    	dataSource.setPassword(properties.getProperty("password"));
    	try {
			dataSource.setFilters(properties.getProperty("filters"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	dataSource.setMaxActive(Integer.parseInt(properties.getProperty("maxActive")));
    	dataSource.setInitialSize(Integer.parseInt(properties.getProperty("initialSize")));
    	dataSource.setMaxWait(Long.parseLong(properties.getProperty("maxWait")));
    	dataSource.setMinIdle(Integer.parseInt(properties.getProperty("minIdle")));
    	
    	dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(properties.getProperty("timeBetweenEvictionRunsMillis")));
    	dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(properties.getProperty("minEvictableIdleTimeMillis")));
    	dataSource.setValidationQuery(properties.getProperty("validationQuery"));
    	dataSource.setTestWhileIdle(Boolean.parseBoolean(properties.getProperty("testWhileIdle")));
    	dataSource.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("testOnBorrow")));
    	dataSource.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("testOnReturn")));
    	dataSource.setPoolPreparedStatements(Boolean.parseBoolean(properties.getProperty("poolPreparedStatements")));
    	dataSource.setMaxOpenPreparedStatements(Integer.parseInt(properties.getProperty("maxOpenPreparedStatements")));
    
    }

}
