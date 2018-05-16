package com.etoak.web.service.crawlservice;


import com.blade.ioc.annotation.Bean;
import com.blade.jdbc.page.Page;
import com.etoak.common.entity.crawl.CrawlPlan;
import com.etoak.common.entity.crawl.CrawlRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Bean
public class CrawlRecordService {

    public List<Map<String,Object>> getRecordPage(String userid  , int currPage , int limit){

        CrawlRecord crawlRecord = new CrawlRecord();
        Page<CrawlRecord> crawlPageData = crawlRecord.where("userid",userid).page(currPage,limit,"createtime desc");

        List<CrawlRecord> records = crawlPageData.getRows();

        List<CrawlPlan> plans = new CrawlPlan().where("userid",userid).findAll() ;

        Map<String,String> planMap = new HashMap<>();
        for(CrawlPlan plan : plans){
            planMap.put( plan.getPlanid() , plan.getPlanname() );
        }

        List< Map<String,Object> >  crlist = new ArrayList<>();
        Map<String,Object> crmap = null ;
        for(CrawlRecord cr : records ){
            crmap = new HashMap<>();
            crmap.put("recordid",cr.getRecordid());
            crmap.put("planid",cr.getPlanid());
            crmap.put("planname",planMap.get(cr.getPlanid()));
            crmap.put("createtime",cr.getCreatetime());
            crmap.put("isfinished",cr.getIsfinished());
            crlist.add(crmap);
        }

        return crlist ;
    }


    public long getTotal(String userid ){
        CrawlRecord crawlRecord = new CrawlRecord();
        return  crawlRecord.where("userid",userid).count();
    }

}
