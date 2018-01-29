package com.etoak.common.aliyunsms;

import com.etoak.common.commonUtil.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
public class AliyunSMSServiceParam {

	private String PhoneNumber ;  // 号码
	private String templateParam ; //短信模版参数
	private String templateType ; //短信模版类型
	
	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public String getTemplateParam() {
		return templateParam;
	}

    public String getTemplateType() {
        return templateType;
    }

	// 尊敬的${lead}领导,有一篇文章,需要您审核.详情如下:标题${title};作者${author}.
	private  String templateKeyWords  ;

	private  String trans2Json(Map<String,String> map){
		StringBuilder strb = new StringBuilder(256);
		strb.append("{");
		String quote = "\"" ;
		String[] keywordsArray = templateKeyWords.split("&");
		for(String keyword : keywordsArray){
			String[] keyvalue = keyword.split("=");
			if(map.containsKey(keyvalue[1])){
				strb.append(quote).append(keyvalue[0]).append(quote).append(":").append(quote).append( (String) map.get(keyvalue[1]) ).append(quote).append(",");
			}else{
                try {
                    throw new Exception("传入的map集合中没有完整包含短信模板所需的数据");
                } catch (Exception e) {
                    log.error("error_argus:" , e);
                }
            }
		}
		strb.deleteCharAt(strb.length()-1);
		strb.append("}");
		return strb.toString();
	} 
	
	/*
	*   map.put("BMZYXM", "某领导");
    *	map.put("FBBT", "关于某某汇报");
    *	map.put("FBR", "某科员");
    *	map.put("DXJSDH", "15011112222");
	*/
	public AliyunSMSServiceParam(Map<String,String> map){
        this.templateType = map.get("SMSTYPE");
        this.PhoneNumber = map.get("PHONENUMBER");
        this.templateKeyWords = PropertiesUtil.getProperty("app.properties","templateKeyWords."+templateType);
        this.templateParam = trans2Json(map);
    }

	@Override
	public String toString() {
		return "AliyunSMSServiceParam{" +
				"PhoneNumber='" + PhoneNumber + '\'' +
				", templateParam='" + templateParam + '\'' +
				", templateType='" + templateType + '\'' +
				", templateKeyWords='" + templateKeyWords + '\'' +
				'}';
	}
}
