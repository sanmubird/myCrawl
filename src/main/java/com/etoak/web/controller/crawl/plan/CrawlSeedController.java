package com.etoak.web.controller.crawl.plan;

import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.page.Page;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.etoak.common.entity.crawl.CrawlSeed;
import com.etoak.web.service.crawlservice.CrawlSeedServie;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("crawl/seed/")
public class CrawlSeedController {


    @Inject
    private CrawlSeedServie crawlSeedServie ;


    @GetRoute("addSeed")
    public String addSeed(){
        return "/crawl/plan/addseed.html";
    }

    @PostRoute("saveSeed")
    @JSON
    public Map<String,String> saveseed( @Param String seedpath , @Param String seedpathdesc , @Param String planid ,@Param String seedid ){

        CrawlSeed crawlSeed = new CrawlSeed();
        crawlSeed.setSeedpath(seedpath);
        crawlSeed.setSeedpathdesc(seedpathdesc);
        crawlSeed.setPlanid(planid);
        if(seedid != null ){
            crawlSeed.setSeedid(seedid);
        }
        crawlSeedServie.save(crawlSeed);

        Map<String,String> saveResultMap = new HashMap<>();
        saveResultMap.put("isSaved","ok");
        return saveResultMap ;
    }


    @GetRoute("getSeedPage")
    @JSON
    public Map<String,Object> getPageSeed( Request request, @Param String planid ){
        Map<String,Object> seedPageMap =  new HashMap<>();
        int limit = request.queryInt("limit", 5);
        int currPage = request.queryInt("offset", 0) / limit + 1;

        CrawlSeed crawlSeed = new CrawlSeed();
        Page<CrawlSeed> seedPageData = crawlSeed.where("planid",planid).page(currPage, limit);

        seedPageMap.put("total", seedPageData.getTotalRows() );// total键 存放总记录数，必须的
        seedPageMap.put("rows", seedPageData.getRows() );// rows键 存放每页记录 list
        return seedPageMap ;
    }


    @GetRoute("getSeedById")
    @JSON
    public CrawlSeed getSeedById(@Param String seedid ){
        CrawlSeed crawlSeed = new CrawlSeed();
        return crawlSeed.find(seedid);
    }

    @GetRoute("deleteSeed")
    @JSON
    public Map<String,String> deleteSeed( @Param String seedid){

        CrawlSeed crawlSeed = new CrawlSeed();
        crawlSeed.delete(seedid);
        Map<String,String> saveResultMap = new HashMap<>();
        saveResultMap.put("isDelete","ok");
        return saveResultMap ;
    }
}
