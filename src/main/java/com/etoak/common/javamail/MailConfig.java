package com.etoak.common.javamail;

import lombok.Data;

import java.util.Map;


@Data
public class MailConfig {

    private String subject ;
    private String toMail ;

    private String attachFilePath ;
    private String attachFiileName ;

    private String templateFilePath ;
    private Map<String,Object> templateMap ;

}
