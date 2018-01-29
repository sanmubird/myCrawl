package com.etoak.common.entity.crawl;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

import java.util.Date;

@Table(value="crawl_record" , pk = "recordid")
@Data
public class CrawlRecord extends ActiveRecord {

    private String recordid ;
    private String planid ;
    private String resultpath ;
    private Date createtime ;
    private String userid ;
    private Boolean isfinished ;
}
