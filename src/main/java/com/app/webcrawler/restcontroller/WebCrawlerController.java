package com.app.webcrawler.restcontroller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.webcrawler.model.PageTree;
import com.app.webcrawler.model.PageTreeInfo;
import com.app.webcrawler.service.CrawlerService;

@RestController
public class WebCrawlerController {
	
	@Autowired
	CrawlerService crawlerService;
	
	@RequestMapping(value="/craweler" , method=RequestMethod.GET)
	public ResponseEntity<PageTree> getWebPageTreeInfo(@NotNull @RequestParam(value = "url", required = true) final String url,
            @RequestParam(value = "depth", required = false) final Integer depth){
		List<PageTreeInfo> list = crawlerService.getPageTreeInfo(url, depth, new ArrayList<PageTreeInfo>());
		PageTree pg = new PageTree();
		pg.setDetails(list);
		pg.setTotalLinks(list.size());
		pg.setTotalImages(getImageCount(list));
		return new ResponseEntity<PageTree>(pg,HttpStatus.OK);
	}

	private static int getImageCount(List<PageTreeInfo> list) {
		int  count =0;
		for(PageTreeInfo pg: list) {
			count = count+ pg.getImageCount();
		}
		return count;
		
	}
}
