package com.etoak.common.javamail;

import com.etoak.common.commonUtil.Chinese2Pinyin;
import com.etoak.common.commonUtil.PropertiesUtil;
import io.github.biezhi.ome.OhMyEmail;
import jetbrick.template.JetEngine;
import jetbrick.template.JetTemplate;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import java.io.File;
import java.io.StringWriter;
import java.util.Properties;

@Slf4j
public class MailMain {

    private static Properties properties;

    private MailConfig mailConfig ;

    private static Properties getProps() {
        if (properties == null) {
            properties = PropertiesUtil.getProps("app.properties");
        }
        return properties;
    }

    public MailMain(MailConfig mailConfig ){
        this.properties = getProps();
        OhMyEmail.config(OhMyEmail.SMTP_QQ(false),properties.getProperty("mail.username","sanmubird@qq.com"),properties.getProperty("mail.password","kwsllagplfdybcdg"));
        this.mailConfig = mailConfig ;
    }


    public void sendRegisterEmail() throws MessagingException {
        String temolateFilePath = mailConfig.getTemplateFilePath() ;
        if( temolateFilePath != null){
            JetEngine engine = JetEngine.create();
            JetTemplate template = engine.getTemplate( temolateFilePath );

            StringWriter writer = new StringWriter();
            template.render(mailConfig.getTemplateMap(), writer);
            String output = writer.toString();
            OhMyEmail.subject(mailConfig.getSubject())
                    .from("三目鸟的QQ邮箱")
                    .to(mailConfig.getToMail())
                    .html(output)
                    .send();
        }
    }


    public void sendCrawlResultEmail() throws MessagingException {
        String temolateFilePath = mailConfig.getTemplateFilePath() ;
        if( temolateFilePath != null){
            JetEngine engine = JetEngine.create();
            JetTemplate template = engine.getTemplate( temolateFilePath );

            StringWriter writer = new StringWriter();
            template.render(mailConfig.getTemplateMap(), writer);
            String output = writer.toString();
            String attachFilePath = mailConfig.getAttachFilePath();
            File attachFile = new File(attachFilePath);
            String attachFileName = mailConfig.getAttachFiileName() ;
            /*经过实际实验发现汉字字数超过三个时,邮件发出的附件不能识别,所以此处采取了将汉字转换为拼音的方法*/
            attachFileName = Chinese2Pinyin.getPingYin(attachFileName);
            OhMyEmail.subject(mailConfig.getSubject())
                    .from("三目鸟的QQ邮箱")
                    .to(mailConfig.getToMail())
                    .html(output)
                    .attach( attachFile , attachFileName  )
                    .send();
        }
    }



}
