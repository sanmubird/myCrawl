package com.etoak.web.controller.crawl.plan;

import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.page.Page;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.etoak.common.entity.crawl.CrawlPlan;
import com.etoak.web.service.crawlservice.CrawlPlanService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("crawl/plan/")
public class CrawlPlanController {

    @Inject
    private CrawlPlanService crawlPlanService ;

    @GetRoute("addcrawltask")
    public String addCrawlTask() { //到达登录页面
        return "/crawl/plan/addcrawltask.html";
    }

    @GetRoute("seealltask")
    public String seealltask() { //到达登录页面
        return "/crawl/plan/seealltask.html";
    }

    @GetRoute("crawlrecord")
    public String crawlhistory() { //到达登录页面
        return "/crawl/plan/crawlrecord.html";
    }

    @PostRoute("savePlan")
    @JSON
    public Map<String,String> saveplan(Request request ,  @Param String planid , @Param String planname , @Param String plandesc , @Param int deptnumber , @Param int topn , @Param String isresumable){
        Map<String,String> saveResultMap = new HashMap<>();
        CrawlPlan crawlPlan = new CrawlPlan();
        crawlPlan.setUserid(request.session().attribute("userid"));
        crawlPlan.setPlanname(planname);
        crawlPlan.setPlandesc(plandesc);
        crawlPlan.setDeptnumber(deptnumber);
        crawlPlan.setTopn(topn);
        crawlPlan.setIsresumable(isresumable.equals("1"));
        if(planid != null ){
            crawlPlan.setPlanid(planid);
            crawlPlan.setCrawlmemorypath(planid);
        }
        String planId = crawlPlanService.save(crawlPlan);
        saveResultMap.put("isSaved","ok");
        saveResultMap.put("planid",planId);
        return saveResultMap ;
    }

    @GetRoute("getPlanPage")
    @JSON
    public Map<String,Object> getPageRegex( Request request, @Param String planid ){
        Map<String,Object> seedPageMap =  new HashMap<>();
        int limit = request.queryInt("limit", 5);
        int currPage = request.queryInt("offset", 0) / limit + 1;

        CrawlPlan crawlPlan = new CrawlPlan();
        crawlPlan.where("1","1");
        if(planid != null ){
            crawlPlan.and("planid",planid);
        }
        Page<CrawlPlan> seedPageData = crawlPlan.and("userid",request.session().attribute("userid")).page(currPage, limit, "createtime desc");
        seedPageMap.put("total", seedPageData.getTotalRows() );// total键 存放总记录数，必须的
        seedPageMap.put("rows", seedPageData.getRows() );// rows键 存放每页记录 list
        return seedPageMap ;
    }

    @GetRoute("deletePlan")
    @JSON
    public Map<String,String>  deletePlan(@Param String planid){
        CrawlPlan crawlPlan = new CrawlPlan();
        crawlPlan.delete(planid);
        Map<String,String> saveResultMap = new HashMap<>();
        saveResultMap.put("isDelete","ok");
        return saveResultMap ;
    }

    @GetRoute("getPlanById")
    @JSON
    public CrawlPlan getPlanById(@Param String planid ){
        CrawlPlan crawlPlan = new CrawlPlan();
        return crawlPlan.find(planid);
    }

}
