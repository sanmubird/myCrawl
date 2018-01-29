package com.etoak.web.controller.crawl;

import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.Param;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Session;
import com.etoak.web.service.crawlservice.CrawlService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("crawl/crawl")
public class CrawlController {

    @Inject
    private CrawlService crawlService;

    @GetRoute("/startCrawl")
    public void start(Request request , @Param String planid)  {

        Session session = request.session();
        String userid = session.attribute("userid");
        String userphone = session.attribute("userphone");
        String username = session.attribute("username");
        String useremail = session.attribute("useremail");
        try {
            crawlService.start(planid,userid,userphone,username,useremail);
        } catch (Exception e) {
            log.error("爬取任务执行失败!", e);
        }
    }

}
