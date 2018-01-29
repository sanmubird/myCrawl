package com.etoak.web.controller.crawl.plan;

import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.page.Page;
import com.blade.mvc.annotation.*;
import com.blade.mvc.http.Request;
import com.etoak.common.entity.crawl.CrawlElement;
import com.etoak.web.service.crawlservice.CrawlElementService;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Path("crawl/element/")
public class CrawlElementController {

    @Inject
    private CrawlElementService crawlElementService;

    @GetRoute("addElement")
    public String addElement(){
        return "/crawl/plan/addelement.html";
    }

    @PostRoute("saveElement")
    @JSON
    public Map<String,String> saveElement(@Param String elementname , @Param String elementcode , @Param String elementdesc , @Param String target ,@Param Integer lastestdays ,@Param String testurl , @Param String planid , @Param String elementid ){
        CrawlElement crawlElement = new CrawlElement();
        crawlElement.setPlanid(planid);

        if(elementid != null ){
            crawlElement.setElementid(elementid);
        }
        if(lastestdays != null ){
            crawlElement.setLastestdays(lastestdays);
        }
        if(testurl != null ){
            crawlElement.setTesturl(testurl);
        }
        crawlElement.setElementname(elementname);
        crawlElement.setElementcode(elementcode);
        crawlElement.setElementdesc(elementdesc);
        crawlElement.setTarget(target);

        crawlElementService.save(crawlElement);

        Map<String,String> saveResultMap = new HashMap<>();
        saveResultMap.put("isSaved","ok");
        return saveResultMap ;
    }


    @GetRoute("getElementPage")
    @JSON
    public Map<String,Object> getElementPage(Request request, @Param String planid ){
        Map<String,Object> seedPageMap =  new HashMap<>();
        int limit = request.queryInt("limit", 5);
        int currPage = request.queryInt("offset", 0) / limit + 1;

        CrawlElement crawlElement = new CrawlElement();
        Page<CrawlElement> seedPageData = crawlElement.where("planid",planid).page(currPage, limit);

        seedPageMap.put("total", seedPageData.getTotalRows() );// total键 存放总记录数，必须的
        seedPageMap.put("rows", seedPageData.getRows() );// rows键 存放每页记录 list
        return seedPageMap ;
    }


    @GetRoute("getElementById")
    @JSON
    public CrawlElement getElementById(@Param String elementid ){
        CrawlElement crawlElement = new CrawlElement();
        return crawlElement.find(elementid);
    }

    @GetRoute("deleteElement")
    @JSON
    public Map<String,String> deleteElement( @Param String elementid){

        CrawlElement crawlElement = new CrawlElement();
        crawlElement.delete(elementid);
        Map<String,String> saveResultMap = new HashMap<>();
        saveResultMap.put("isDelete","ok");
        return saveResultMap ;
    }

    @GetRoute("testElement")
    @JSON
    public Map<String,String> testElement(@Param String target , @Param String testurl , @Param String elementcode){
        Map<String,String> map =  new HashMap<>();
        map.put("TARGET",target);
        map.put("CODE",elementcode);
        map.put("URL",testurl);
        return crawlElementService.testElement(map);
    }
}
