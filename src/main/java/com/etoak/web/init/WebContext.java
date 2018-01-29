package com.etoak.web.init;


import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.ioc.annotation.Bean;
import com.blade.jdbc.Base;
import com.etoak.common.jdbc.connectionpool.ConnectionPool;
import org.sql2o.Sql2o;



/**
 * Tale初始化进程
 *
 * @author biezhi
 */
@Bean
public class WebContext implements BeanProcessor {


    @Override
    public void preHandle(Blade blade) {
        //这里设置了数据源
        Sql2o sql2o = new Sql2o(ConnectionPool.getDataSource());
        Base.open(sql2o);
    }

    @Override
    public void processor(Blade blade) {
    }
}
