package com.etoak.common.entity.crawl;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

import java.util.Date;

@Table(value = "crawl_seed" , pk = "seedid")
@Data
public class CrawlSeed extends ActiveRecord {

    private String seedid;
    private String planid;
    private String seedpath;
    private String seedpathdesc;
    private Date createtime;
    private Boolean enable;
}
