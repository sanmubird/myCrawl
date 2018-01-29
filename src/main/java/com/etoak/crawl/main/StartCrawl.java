package com.etoak.crawl.main;

import com.blade.jdbc.core.ActiveRecord;
import com.blade.kit.UUID;
import com.etoak.common.commonUtil.DateUtil;
import com.etoak.common.entity.crawl.*;
import com.etoak.crawl.config.CrawlConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartCrawl {


    public void start(String planid , String userid , String userphone , String username , String useremial ) throws Exception {

        CrawlConfig cf = getCrawlConfig(planid , userid ,  userphone , username, useremial);


        CrawlRecord crawlRecord = new CrawlRecord();
        crawlRecord.setRecordid(cf.getRecordid());
        crawlRecord.setPlanid(planid);
        crawlRecord.setUserid(userid);
        crawlRecord.setCreatetime(DateUtil.getDate());
        crawlRecord.setIsfinished(false);
        crawlRecord.save();
        CommonCrawl cc = new CommonCrawl(cf);

        cc.start(cf.getDeptNumber());


    }


    /*获取爬取配置*/
    private CrawlConfig getCrawlConfig(String planid , String userid , String userphone ,  String username , String useremial ){
        CrawlConfig cf = new CrawlConfig();

        cf.setPhone(userphone);
        cf.setUserid(userid);
        cf.setUsername(username);
        cf.setUseremail(useremial);

        CrawlPlan crawlPlan = new CrawlPlan();
        CrawlPlan cp = crawlPlan.where("planid",planid).find();
        cf.setAutoParse(cp.getIsautoparse());
        cf.setResumable( cp.getIsresumable());
        cf.setCrawlPath(cp.getCrawlmemorypath());
        cf.setConnectTimeout(cp.getConnecttimeout());
        cf.setDeptNumber(cp.getDeptnumber());
        cf.setThreadNumber(cp.getThreadnumber());
        cf.setTopN(cp.getTopn());
        cf.setMaxcontentlength(cp.getMaxcontentlength());
        cf.setPlanid(cp.getPlanid());
        cf.setPlanname(cp.getPlanname());

        Map<String,Object> map = isHasLastestDays(planid);
        cf.setHasLastestDays((boolean) map.get("isHasLastestDays"));
        cf.setLastestDays((int) map.get("lastestDays") );
        cf.setLastestDaysTarget((String) map.get("lastestDaysTarget"));

        cf.setSeeds ( (List<CrawlSeed>) getRecords(CrawlSeed.class,planid));
        cf.setRegexs( (List<CrawlRegex>) getRecords(CrawlRegex.class,planid));
        cf.setElements( (List<CrawlElement>) getRecords(CrawlElement.class,planid) );

        cf.setRecordid( UUID.UU32());

        cf.setBeginTime(DateUtil.getDateTImeStr());

        return cf ;
    }


    /*判断是否规定了爬取最近多少天*/
    public Map<String,Object> isHasLastestDays(String planId){
        Map<String,Object> map = new HashMap<>();
        int n = 0 ;
        boolean b = false ;
        String target = null ;
        List<CrawlElement> elements = (List<CrawlElement>) getRecords(CrawlElement.class,planId);
        for(CrawlElement element : elements){
            if(element.getElementcode().toLowerCase().endsWith("time") && element.getLastestdays() !=  null ){
                b = true ;
                n = element.getLastestdays() ;
                target = element.getTarget();
                break ;
            }
        }
        map.put("isHasLastestDays",b);
        map.put("lastestDays",n);
        map.put("lastestDaysTarget",target);
        return map ;
    }


    /* ? super T  : 更适合往里放东西 , ? extends T : 更适合往外取东西 */
    public  List<?> getRecords(Class< ? extends ActiveRecord> c, String planid ) {
        List<?> list = null;
        try {
            list = c.newInstance().where("planid",planid).findAll();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return list;
    }


}
