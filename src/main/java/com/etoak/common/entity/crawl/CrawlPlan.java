package com.etoak.common.entity.crawl;


import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

import java.util.Date;

@Table(value = "crawl_plan" , pk = "planid")
@Data
public class CrawlPlan extends ActiveRecord{

    private String planid;
    private String userid ;
    private String planname;
    private String plandesc;
    private String crawlmemorypath;

    private Date createtime;

    private Integer threadnumber;
    private Integer topn;
    private Integer connecttimeout;
    private Integer deptnumber;

    private Boolean enable;
    private Boolean isresumable;
    private Boolean isautoparse;

    private Integer maxcontentlength ;

}
