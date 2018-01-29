package com.etoak.web.service.crawlservice;

import com.blade.ioc.annotation.Bean;
import com.blade.kit.UUID;
import com.etoak.common.entity.crawl.CrawlSeed;

import java.util.Date;

@Bean
public class CrawlSeedServie {


    public void save(CrawlSeed crawlSeed){

        crawlSeed.setCreatetime(new Date());
        crawlSeed.setEnable(true);

        if(crawlSeed.getSeedid() == null ){
            crawlSeed.setSeedid(UUID.UU32());
            crawlSeed.save();
        }else{
            crawlSeed.update(crawlSeed.getSeedid());
        }
    }

}
