package com.etoak.crawl.test;

import com.aliyuncs.exceptions.ClientException;
import com.etoak.common.aliyunsms.AliyunSMSService;
import com.etoak.common.aliyunsms.AliyunSMSServiceParam;
import com.etoak.common.commonUtil.DateUtil;
import com.etoak.common.javamail.MailConfig;
import com.etoak.common.javamail.MailMain;
import com.github.crab2died.ExcelUtils;

import javax.mail.MessagingException;
import java.io.File;
import java.util.*;
import java.util.concurrent.CountDownLatch;


public class Test {
    public static void main(String[] args) throws Exception {
        CountDownLatch cdl = new CountDownLatch(4);

    }

    public static void testFileName(){
        String filePath = "d:\\cdfd\\sdas\\dsadas.xfd";
        String filename = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        System.out.println(filename);
    }


    public static void testList2Excel() throws Exception {

        List<List<String>> list2 = new ArrayList<>();
        List<String> header = new ArrayList<>();
        for(int i = 0; i < 3 ; i++){
            header.add(i + "---");
        }
        for (int i = 0; i < 20 ; i++) {
            List<String> _list = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                _list.add(i + " -- " + j);
            }
            list2.add(_list);
        }
        ExcelUtils.getInstance().exportObjects2Excel(list2, header, "C:\\Users\\Administrator\\Desktop\\D.xlsx");
    }


    private static void sendSMS(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("SMSTYPE","advice");
        map.put("PHONENUMBER", "17090130391");
        map.put("CREATETIME", DateUtil.getDateTImeStr());
        map.put("USERNAME","三目鸟");
        map.put("EMIAL","751425278@qq.com");
        AliyunSMSServiceParam sp = new AliyunSMSServiceParam(map);
        System.out.println(sp);
        try {
            AliyunSMSService.sendSms(sp);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }


    public static void testEmail() throws MessagingException {
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("username", "biezhi");
        context.put("email", "sanmubird@qq.com");
        context.put("url", "<a href='http://java-china.org'>https://java-china.org/active/asdkjajdasjdkaweoi</a>");

        MailConfig mc = new MailConfig();
        mc.setSubject("爬取方案名称+日期+爬取结果通知");
        mc.setTemplateFilePath("/mailtemp/cawlresult.jetx");
        mc.setToMail("17090130391@163.com");
        mc.setTemplateMap(context);
        String filePath = "C:\\Users\\Administrator\\Desktop\\第一次正式2018-01-11.xlsx";
        mc.setAttachFilePath(filePath);
        mc.setAttachFiileName("第一次正式2018-01-11.xlsx");

        MailMain mm = new MailMain(mc);
        mm.sendCrawlResultEmail();
    }

}
