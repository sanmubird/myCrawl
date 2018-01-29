package com.etoak.common.entity.crawl;

import com.blade.jdbc.annotation.Table;
import com.blade.jdbc.core.ActiveRecord;
import lombok.Data;

import java.util.Date;

@Table(value = "crawl_element" , pk = "elementid")
@Data
public class CrawlElement extends ActiveRecord {

    private String elementid;
    private String planid;
    private String elementname;
    private String elementcode;
    private String elementdesc;
    private Integer lastestdays ;
    private String target;
    private Date createtime;
    private Boolean enable;
    private String testurl ;
}
