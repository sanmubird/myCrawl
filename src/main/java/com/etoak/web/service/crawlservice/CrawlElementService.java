package com.etoak.web.service.crawlservice;

import com.blade.ioc.annotation.Bean;
import com.blade.kit.UUID;
import com.etoak.common.entity.crawl.CrawlElement;
import com.etoak.crawl.test.TestCrawl;

import java.util.Date;
import java.util.Map;

@Bean
public class CrawlElementService {

    public void save(CrawlElement crawlElement){

        crawlElement.setCreatetime(new Date());
        crawlElement.setEnable(true);

        if(crawlElement.getElementid() == null ){
            crawlElement.setElementid(UUID.UU32());
            crawlElement.save();
        }else{
            crawlElement.update(crawlElement.getElementid());
        }
    }

    public Map<String,String> testElement(Map<String,String> map){
        return TestCrawl.getCrawlContent(map);
    }
}
