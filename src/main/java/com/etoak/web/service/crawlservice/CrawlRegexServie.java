package com.etoak.web.service.crawlservice;

import com.blade.ioc.annotation.Bean;
import com.blade.kit.UUID;
import com.etoak.common.entity.crawl.CrawlRegex;

import java.util.Date;

@Bean
public class CrawlRegexServie {


    public void save(CrawlRegex crawlRegex){

        crawlRegex.setCreatetime(new Date());
        crawlRegex.setEnable(true);

        if(crawlRegex.getRegexid() == null ){
            crawlRegex.setRegexid(UUID.UU32());
            crawlRegex.save();
        }else{
            crawlRegex.update(crawlRegex.getRegexid());
        }
    }

}
