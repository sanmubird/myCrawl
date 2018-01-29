package com.etoak.web.service.crawlservice;

import com.blade.ioc.annotation.Bean;
import com.blade.kit.UUID;
import com.etoak.common.entity.crawl.CrawlPlan;

import java.util.Date;

@Bean
public class CrawlPlanService {

    public String  save(CrawlPlan crawlPlan){
        String crawlPlanid = null ;
        if(crawlPlan.getPlanid() == null ){
            crawlPlanid = UUID.UU32() ;
            crawlPlan.setConnecttimeout(60000);
            crawlPlan.setCrawlmemorypath(crawlPlanid);
            crawlPlan.setCreatetime(new Date());
            crawlPlan.setEnable(true);
            crawlPlan.setThreadnumber(1);
            crawlPlan.setIsautoparse(true);
            crawlPlan.setMaxcontentlength(500);
            crawlPlan.setPlanid(crawlPlanid);
            crawlPlan.save();
        }else{
            crawlPlanid = crawlPlan.getPlanid() ;
            crawlPlan.update(crawlPlanid );
        }
        return crawlPlanid ;
    }

}
