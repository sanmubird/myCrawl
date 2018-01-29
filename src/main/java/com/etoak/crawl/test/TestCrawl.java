package com.etoak.crawl.test;

import java.util.HashMap;
import java.util.Map;

import com.etoak.common.commonUtil.DateUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestCrawl {

	private String selector;

	private Page page;

	private String elementcode ;

	private TestCrawl(String url, String selector , String elementcode) {
		this.selector = selector;
		this.elementcode = elementcode ;
		this.page = RequestAndResponseTool.sendRequstAndGetResponse(url);
	}

	public static Map<String,String> getCrawlContent(Map<String,String> map) {
		TestCrawl tc = new TestCrawl( map.get("URL"),  map.get("TARGET") , map.get("CODE"));
		return tc.testCrawl();
	}

	private Map<String,String> testCrawl() {
		Map<String,String> map = new HashMap<String,String>(); 
		String content = "当前抓取规则没有抓取到任何内容";
		// 提供一种 可以定位元素集合下标的方法 比如h1@1 表示:当前选择器下至少有一个h1元素, 选取出 h1标签集合中 第二个元素;
		if(selector.endsWith("@")){
			map.put("crawlresult", "抓取规则不能以@结尾.");
			return map ;
		}
		String[] selectArgs = selector.split("@");
		if ( selectArgs.length > 2 ) {
			map.put("crawlresult", "抓取规则最多只能包含一个@.");
			return map ;
		}
		Elements es = null ;
		if(selectArgs[0].contains(":")){ // 此处用于有时会使用伪选择器的情况;
			if(selectArgs[0].endsWith(":")){
				map.put("crawlresult", "抓取规则不能以:结尾.");
				return map;
			}
			es = page.select(selectArgs[0]) ;
			if ( es.size() == 0 ) { // 判断当前条件是否存在这样的选择结果,如果i  > 0 则说明有符合刷选条件的结果;
				Document doc = page.getDoc();
				String docStr = doc.body().text();
				if(docStr.length() < 20){
					map.put("crawlresult", "当前地址不允许被抓取.");
					return map;
				}else{
					map.put("crawlresult",  content );
					return map;
				}
			}else{
				content = es.get(0).text();
			}
		}else{
			int selectIndex = 0;
			if (selectArgs.length == 2) {
				int n = Integer.parseInt(selectArgs[1]);
				if (n < 1) {
					map.put("crawlresult", "@后面应该为正整数");
					return map ;
				}
				selectIndex = n - 1 ;
			}
			selector = selectArgs[0].trim() + ":eq(" + selectIndex + ")";
			es = page.select(selector) ;
			if ( es.size() == 0  ) { // 判断当前条件是否存在这样的选择结果,如果i  > 0 则说明有符合刷选条件的结果;
				es = page.select(selectArgs[0].trim()) ;
				int size = es.size() ;
				if ( size == 0  ){
					Document doc = page.getDoc();
					String docStr = doc.body().text();
					if(docStr.length() < 20){
						map.put("crawlresult", "当前地址不允许被抓取.");
						return map ;
					}else{
						map.put("crawlresult",  content );
						return map ;
					}
				}else{
					if( size <= selectIndex){
						content = "当前页面中符合抓取规则元素的个数一共为:"+ size +"个,抓取规则@后面的数子不应超过:"+ size ;
					}else{
						content = es.get(selectIndex).text();
					}
				}
			}else{
				content = es.get(0).text();
			}
		}

		int l = content.length() ;
		if( l > 60 ){
			content = content.substring(0, 60) + "...注意:本网站目前支持存储最大文本长度是500个汉字,如有特殊需求,请联系管理员,当前抓取的文本长度为:"+ l ;
		}


		if(elementcode.endsWith("time")){
            if(DateUtil.isDateTime(content)){
                content += "; 另外,经过智能识别,当前抓取的内容是时间." ;
            }else{
                content += ";  另外,经过智能识别,当前抓取的内容不是时间." ;
            }
        }



		map.put("crawlresult",  content );
		return map ;
	}
	


}
