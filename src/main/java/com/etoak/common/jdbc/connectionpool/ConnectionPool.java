package com.etoak.common.jdbc.connectionpool;

import com.etoak.common.commonUtil.PropertiesUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

public class ConnectionPool {

    private static Properties props;

    private static HikariDataSource dataSource;

    //单例模式读取属性文件;
    private static Properties getProps() {
        if (props == null) {
            props = PropertiesUtil.getProps("jdbc/hikari.properties");
        }
        return props;
    }

    private static HikariDataSource getDs(){
        props =  getProps();
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(props.getProperty("driverClassName", "com.mysql.jdbc.Driver"));
        config.setJdbcUrl(props.getProperty("jdbcUrl", "jdbc:mysql://localhost:3306/mycrawls?useUnicode=true&characterEncoding=utf-8&useSSL=false"));
        config.setUsername(props.getProperty("username", "root"));
        config.setPassword(props.getProperty("password", "sanmubird"));
        config.addDataSourceProperty("cachePrepStmts", props.getProperty("dataSource.cachePrepStmts", "true"));
        config.addDataSourceProperty("prepStmtCacheSize", props.getProperty("dataSource.prepStmtCacheSize", "250"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", props.getProperty("dataSource.prepStmtCacheSqlLimit", "2048"));
        return  new HikariDataSource(config);
    }


    //数据源单例模式;
    public static HikariDataSource getDataSource() {
        if( dataSource == null ){
            dataSource =  getDs();
        }
        return dataSource ;
    }


}
