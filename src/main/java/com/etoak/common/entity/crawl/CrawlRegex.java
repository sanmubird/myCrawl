package com.etoak.common.entity.crawl;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

import java.util.Date;

@Table( value = "crawl_regex" , pk = "regexid")
@Data
public class CrawlRegex extends ActiveRecord {

    private String regexid;
    private String planid;
    private String regexpath;
    private String regexpathdesc;
    private Date createtime;
    private Boolean enable;

}
