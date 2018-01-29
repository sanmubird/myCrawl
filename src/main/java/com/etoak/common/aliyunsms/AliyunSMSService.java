package com.etoak.common.aliyunsms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.etoak.common.commonUtil.DateUtil;
import com.etoak.common.commonUtil.PropertiesUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AliyunSMSService {


    private static final Properties props;
    private static IAcsClient acsClient;

    static {
        props = PropertiesUtil.getProps("app.properties");
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", props.getProperty("sun.net.client.defaultConnectTimeout", "10000"));
        System.setProperty("sun.net.client.defaultReadTimeout", props.getProperty("sun.net.client.defaultReadTimeout", "10000"));

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", props.getProperty("accessKeyId" ), props.getProperty("accessKeySecret"));
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", props.getProperty("product", "Dysmsapi"), props.getProperty("domain", "dysmsapi.aliyuncs.com"));
        } catch (ClientException e) {
            e.printStackTrace();
        }
        acsClient = new DefaultAcsClient(profile);
    }

    //发送短信
    public static SendSmsResponse sendSms(AliyunSMSServiceParam sp) throws ClientException {

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(sp.getPhoneNumber());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(props.getProperty("signName"));
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(props.getProperty("templateCode." + sp.getTemplateType()));
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(sp.getTemplateParam());
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }


}
