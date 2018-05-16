package com.etoak.web.controller.crawl.plan;


import com.blade.ioc.annotation.Inject;
import com.blade.mvc.annotation.GetRoute;
import com.blade.mvc.annotation.JSON;
import com.blade.mvc.annotation.Param;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import com.etoak.common.entity.crawl.CrawlRecord;
import com.etoak.web.service.crawlservice.CrawlRecordService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Path("crawl/record/")
public class CrawlRecordController {


	@Inject
	private CrawlRecordService crawlRecordService;

	@GetRoute("getRecordPage")
	@JSON
	public Map<String, Object> getRecordPage(Request request) {
		Map<String, Object> seedPageMap = new HashMap<>();
		int limit = request.queryInt("limit", 5);
		int currPage = request.queryInt("offset", 0) / limit + 1;

		String userid = request.session().attribute("userid");

		long count = crawlRecordService.getTotal(userid);
		List<Map<String, Object>> rercordlist = crawlRecordService.getRecordPage(userid, currPage, limit);

		seedPageMap.put("total", count);// total键 存放总记录数，必须的
		seedPageMap.put("rows", rercordlist);// rows键 存放每页记录 list
		return seedPageMap;
	}


	@GetRoute("downloadResultById")
	public void downloadResultById(Response response, @Param String recordid) {
		CrawlRecord crawlRecord = new CrawlRecord();
		CrawlRecord cr = crawlRecord.find(recordid);
		String filePath = cr.getResultpath();
		String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
		try {
			response.download(fileName, new File(filePath));
		} catch (Exception e) {
			log.error("下载爬取结果失败!", e);
		}
	}

	@GetRoute("deleteRecordById")
	@JSON
	public Map<String, String> deleteRecordById(Response response, @Param String recordid) {
		CrawlRecord crawlRecord = new CrawlRecord();
		crawlRecord.delete(recordid);
		Map<String, String> saveResultMap = new HashMap<>();
		saveResultMap.put("isDelete", "ok");
		return saveResultMap;
	}
}
