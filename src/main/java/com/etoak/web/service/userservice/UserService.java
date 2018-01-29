package com.etoak.web.service.userservice;

import com.blade.ioc.annotation.Bean;
import com.etoak.common.javamail.MailConfig;
import com.etoak.common.javamail.MailMain;
import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Bean
public class UserService {

    public void senRegisterEmail(String  username , String email ){
        new Thread(){
            public void run(){
                Map<String, Object> context = new HashMap<String, Object>();
                context.put("username", username );
                MailConfig mc = new MailConfig();
                mc.setTemplateMap(context);
                mc.setSubject("注册成功通知");
                mc.setTemplateFilePath("/mailtemp/register.jetx");
                mc.setToMail(email);

                MailMain mm = new MailMain(mc);
                try {
                    mm.sendRegisterEmail();
                } catch (MessagingException e) {
                   log.error("发送注册成功邮件通知失败",e);
                }
            }
        }.start();
    }


    public void senUpdateInfoEmail(String  username , String email ){
        new Thread(){
            public void run(){
                Map<String, Object> context = new HashMap<String, Object>();
                context.put("username", username );
                MailConfig mc = new MailConfig();
                mc.setTemplateMap(context);
                mc.setSubject("修改个人资料成功通知");
                mc.setTemplateFilePath("/mailtemp/updateInfo.jetx");
                mc.setToMail(email);

                MailMain mm = new MailMain(mc);
                try {
                    mm.sendRegisterEmail();
                } catch (MessagingException e) {
                    log.error("发送修改个人资料邮件通知失败",e);
                }

            }
        }.start();
    }
}
