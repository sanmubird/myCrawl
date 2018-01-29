package com.etoak.web.controller.crawl.plan;

import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.page.Page;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.etoak.common.entity.crawl.CrawlRegex;
import com.etoak.web.service.crawlservice.CrawlRegexServie;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("crawl/regex/")
public class CrawlRegexController {


    @Inject
    private CrawlRegexServie crawlRegexServie;

    @GetRoute("addRegex")
    public String addRegex(){
        return "/crawl/plan/addregex.html";
    }

    @PostRoute("saveRegex")
    @JSON
    public Map<String,String> saveRegex( @Param String regexpath , @Param String regexpathdesc , @Param String planid ,@Param String regexid ){

        CrawlRegex crawlRegex = new CrawlRegex();
        crawlRegex.setRegexpath(regexpath);
        crawlRegex.setRegexpathdesc(regexpathdesc);
        crawlRegex.setPlanid(planid);
        if(regexid != null ){
            crawlRegex.setRegexid(regexid);
        }
        crawlRegexServie.save(crawlRegex);

        Map<String,String> saveResultMap = new HashMap<>();
        saveResultMap.put("isSaved","ok");
        return saveResultMap ;
    }


    @GetRoute("getRegexPage")
    @JSON
    public Map<String,Object> getPageRegex( Request request, @Param String planid ){
        Map<String,Object> seedPageMap =  new HashMap<>();
        int limit = request.queryInt("limit", 5);
        int currPage = request.queryInt("offset", 0) / limit + 1;

        CrawlRegex crawlRegex = new CrawlRegex();
        Page<CrawlRegex> seedPageData = crawlRegex.where("planid",planid).page(currPage, limit);

        seedPageMap.put("total", seedPageData.getTotalRows() );// total键 存放总记录数，必须的
        seedPageMap.put("rows", seedPageData.getRows() );// rows键 存放每页记录 list
        return seedPageMap ;
    }


    @GetRoute("getRegexById")
    @JSON
    public CrawlRegex getRegexById(@Param String regexid ){
        CrawlRegex crawlRegex = new CrawlRegex();
        return crawlRegex.find(regexid);
    }

    @GetRoute("deleteRegex")
    @JSON
    public Map<String,String> deleteRegex( @Param String regexid){

        CrawlRegex crawlRegex = new CrawlRegex();
        crawlRegex.delete(regexid);
        Map<String,String> saveResultMap = new HashMap<>();
        saveResultMap.put("isDelete","ok");
        return saveResultMap ;
    }
}
