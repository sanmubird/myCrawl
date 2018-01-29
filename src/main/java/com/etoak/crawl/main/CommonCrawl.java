package com.etoak.crawl.main;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import com.aliyuncs.exceptions.ClientException;
import com.blade.jdbc.core.OrderBy;
import com.blade.kit.UUID;
import com.etoak.common.aliyunsms.AliyunSMSService;
import com.etoak.common.aliyunsms.AliyunSMSServiceParam;
import com.etoak.common.commonUtil.DateUtil;
import com.etoak.common.commonUtil.FileUtil;
import com.etoak.common.entity.crawl.*;
import com.etoak.common.javamail.MailConfig;
import com.etoak.common.javamail.MailMain;
import com.etoak.crawl.config.CrawlConfig;
import com.github.crab2died.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.select.Elements;

import javax.mail.MessagingException;
import java.io.File;
import java.util.*;

@Slf4j
public class CommonCrawl extends BreadthCrawler {

    private List<CrawlElement> elements;
    private boolean isHasLastestDays ;
    private int lastestDays ;
    private String lastestDaysTarget ;
    private String recordid ;
    private int maxcontentlength ;

    private String planid ;
    private String planname ;

    private String phone ;
    private String userid ;
    private String username ;
    private String useremail ;

    private String beginTime ;

    public CommonCrawl(CrawlConfig config) {
        this(config.getCrawlPath(), config.isAutoParse());

        for (CrawlSeed seed : config.getSeeds()) {
            this.addSeed(seed.getSeedpath());
        }
        for (CrawlRegex regex : config.getRegexs()) {
            this.addRegex(regex.getRegexpath());
        }

        this.setThreads(config.getThreadNumber());
        this.setResumable(config.isResumable());

        this.getConf().setTopN(config.getTopN());
        this.getConf().setConnectTimeout(config.getConnectTimeout());

        this.elements = config.getElements();

        this.isHasLastestDays = config.isHasLastestDays();
        this.lastestDays = config.getLastestDays();
        this.lastestDaysTarget = config.getLastestDaysTarget();
        this.maxcontentlength = config.getMaxcontentlength();

        this.recordid = config.getRecordid() ;

        this.planid = config.getPlanid();
        this.planname = config.getPlanname();

        this.phone = config.getPhone();
        this.userid = config.getUserid();
        this.username = config.getUsername();
        this.useremail = config.getUseremail();

        this.beginTime = config.getBeginTime();
    }

    public CommonCrawl(String crawlPath, boolean autoParse) {
        super(crawlPath, autoParse);
    }

    @Override
    public void visit(Page page, CrawlDatums crawlDatums) {
        String url = page.url();
        Date date = DateUtil.getDate();
        if(!elements.isEmpty()){  // 抓取元素不能为null ;
            if(isHasLastestDays){  //抓取最近天数不为空,先去抓取日期,判断是否再抓取日期范围内,如果在,则继续,如果不在,则直接返回,不做处理.
                String dataStr = getSelectorContent(page , lastestDaysTarget );
                boolean b = DateUtil.isAfterNowSomeDays(dataStr,lastestDays);
                if(!b){
                    return ;
                }
            }

            /*抓取各个爬取目标,并持久化到数据库 */
            for(CrawlElement element : elements){
                String target = element.getTarget();
                String content = getSelectorContent(page,target);
                if( content == null){
                    break;
                }
                if(element.getElementcode().toLowerCase().endsWith("time")){
                    content = DateUtil.formatter2StandardDatestr(content) ;
                }
                CrawlResult crawlResult = new CrawlResult();
                crawlResult.setResultid(UUID.UU32());
                crawlResult.setElementid(element.getElementid());
                crawlResult.setRecordid(recordid);
                crawlResult.setCreatetime(date);
                crawlResult.setElementcode(element.getElementcode());
                crawlResult.setUrl(url);
                crawlResult.setContent( content.length() > maxcontentlength ? content.substring(0,maxcontentlength) : content );
                crawlResult.save();
            }
        }else{
            stop();
        }
    }

    //获取选择器的内容;  此处不应该再来考虑target是否合规的问题.
    private String getSelectorContent(Page page , String target ){
        String[] selectArgs = target.split("@");
        Elements es = null;
        if (selectArgs[0].contains(":")) { // 此处用于有时会使用伪选择器的情况;
            es = page.select(selectArgs[0]);
            if (es.size() == 0) { // 判断当前条件是否存在这样的选择结果
                return null;
            }else{
                return es.get(0).text();
            }
        } else {
            int selectIndex = 0;
            if (selectArgs.length == 2) {
                selectIndex = Integer.parseInt(selectArgs[1]) - 1;
            }
            target = selectArgs[0].trim() + ":eq(" + selectIndex + ")";
            es = page.select(target);
            if (es.size() == 0) { // 判断当前条件是否存在这样的选择结果
                es = page.select(selectArgs[0].trim()) ;
                if ( es.size() == 0  ){
                    return null ;
                }else{
                    return es.get(selectIndex).text();
                }
            }else{
                return es.get(0).text();
            }
        }
    }


    @Override
    public void afterStop(){
        CrawlResult crawlResult = new CrawlResult();
        List<CrawlResult> results = crawlResult.where("recordid",recordid).findAll(OrderBy.asc("url"));
        if(!results.isEmpty()){
            List<Map<String,String>> objectList =  megerResult2Object(results);
            List<String> header =  getHeader() ;
            List<List<String>> pageList = convertMap2List(objectList , header );
            String dirPath = getDirPath();
            mkdirs(dirPath);
            String filename = getFileName();
            String filePath = dirPath + filename ;
            try {
                ExcelUtils.getInstance().exportObjects2Excel(pageList, header, filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            CrawlRecord crawlRecord = new CrawlRecord();
            crawlRecord.setResultpath(filePath);
            crawlRecord.setIsfinished(true);
            crawlRecord.update(recordid);
            crawlResult.where("recordid",recordid).delete();
            senEmail(filePath,filename);
            sendSMS();
        }
    }

    /*发送短信*/
    private void sendSMS(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("SMSTYPE","advice");
        map.put("PHONENUMBER", phone);
        map.put("TIME",beginTime);
        map.put("USERNAME",username);
        AliyunSMSServiceParam sp = new AliyunSMSServiceParam(map);
        try {
            AliyunSMSService.sendSms(sp);
        } catch (ClientException e) {
            log.error("发送查询结果短信通知失败",e);
        }
    }

    /*发送邮件*/
    private void senEmail(String  filePath, String filename){
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", username);

        MailConfig mc = new MailConfig();
        mc.setSubject("爬取结果通知:"+planname);
        mc.setTemplateFilePath("/mailtemp/cawlresult.jetx");
        mc.setToMail(useremail);
        mc.setTemplateMap(context);
        mc.setAttachFiileName(filename);
        mc.setAttachFilePath(filePath);

        MailMain mm = new MailMain(mc);
        try {
            mm.sendCrawlResultEmail();
        } catch (MessagingException e) {
           log.error("发送查询结果邮件通知失败",e);
        }
    }


    /*根路径/文件夹路径/用户手机号/方案id/记录id/方案名+日期;*/
    private String getDirPath(){
        StringBuffer filePathStr = new StringBuffer(256);
        filePathStr.append(FileUtil.CLASSPATH).append("CrawlResultFile").append(File.separator)
                .append(phone).append(File.separator).append(planid).append(File.separator).append(recordid)
                .append(File.separator);
        return   filePathStr.toString() ;
    }

    /*得到文件路径*/
    private String getFileName(){
        StringBuffer filePathStr = new StringBuffer(128);
        filePathStr.append(planname).append(DateUtil.getDateDayStr()).append(".xlsx");
        return   filePathStr.toString() ;
    }

    /*创建目录*/
    private void mkdirs(String dirPath){
        File dir = new File(dirPath);
        if(!dir.exists()){
            dir.mkdirs();
        }
    }

    /*将map转换成list*/
    private List<List<String>> convertMap2List(List<Map<String,String>> objectList , List<String> header ){
        List<List<String>> pageList = new ArrayList<>();
        List<String> rowList = null ;
        for(Iterator<Map<String,String>> it = objectList.iterator();it.hasNext();){
            rowList = new ArrayList<>();
            Map<String,String> map = it.next();
            for(String s : header ){
                String sv = map.get(s) ;
                rowList.add( sv == null ? "" : sv );
            }
            pageList.add(rowList);
        }
        return pageList ;
    }

    /*得到头部*/
    private List<String> getHeader(){
        List<String> header = new ArrayList();
        for(CrawlElement element:elements){
            header.add(element.getElementcode());
        }
        return header ;
    }



    /*合并同一个url的抓取结果*/
    private List<Map<String,String>> megerResult2Object(List<CrawlResult> results){
        if(results.isEmpty()){
            return null ;
        }
        String lastUrl = "" ;//以减少下面在循环中判断 null 的次数;
        List<Map<String,String>> objectList = new ArrayList<Map<String,String>>();

        Map<String,String> objectMap = null ;

        for(Iterator<CrawlResult> it = results.iterator();it.hasNext();){
            CrawlResult resultMap = it.next();
            String url =  resultMap.getUrl();
            if(lastUrl.equals(url)){
                objectMap.put(resultMap.getElementcode(),resultMap.getContent());
                lastUrl = url ;
            }else{
                if( url != null && url.startsWith("http")  ){
                    objectList.add(objectMap);
                    objectMap = new HashMap<String,String>();
                    objectMap.put(resultMap.getElementcode(),resultMap.getContent());
                    lastUrl = url ;
                }
            }
        }
        if(!objectList.isEmpty()){
            objectList.remove(0);
        }
        return objectList ;
    }

}
