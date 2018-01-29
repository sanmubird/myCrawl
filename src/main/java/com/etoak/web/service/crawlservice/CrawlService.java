package com.etoak.web.service.crawlservice;

import com.blade.ioc.annotation.Bean;
import com.etoak.crawl.main.StartCrawl;

@Bean
public class CrawlService {

    public void start(String planid , String userid , String userphone , String username , String useremial  ) throws Exception {
        StartCrawl startCrawl = new StartCrawl();
        startCrawl.start(planid,userid,userphone ,username , useremial  );
    }

}
