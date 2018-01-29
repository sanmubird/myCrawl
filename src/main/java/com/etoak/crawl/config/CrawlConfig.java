package com.etoak.crawl.config;

import com.etoak.common.entity.crawl.CrawlElement;
import com.etoak.common.entity.crawl.CrawlRegex;
import com.etoak.common.entity.crawl.CrawlSeed;
import lombok.Data;

import java.util.List;

@Data
public class CrawlConfig {

    private List<CrawlSeed> seeds; //种子地址
    private List<CrawlRegex> regexs;//过滤地址(符合过滤地址的才进行爬取)
    private List<CrawlElement> elements; //查找相应的模块

    private boolean isResumable ;
    private int threadNumber;

    private int topN;
    private int connectTimeout;

    private boolean isAutoParse ;
    private String crawlPath ;

    private int lastestDays;
    private boolean isHasLastestDays ;
    private String lastestDaysTarget ;

    private int deptNumber;

    private String recordid ;
    private int maxcontentlength ;

    private String phone ;
    private String userid ;
    private String username ;
    private String useremail ;

    private String planid ;
    private String planname ;

    private String beginTime ;
}
