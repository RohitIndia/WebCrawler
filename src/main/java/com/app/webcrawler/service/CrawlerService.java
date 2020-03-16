package com.app.webcrawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import com.app.webcrawler.model.PageTreeInfo;

@Service
public class CrawlerService {

	private HashSet<String> links;

	public List<PageTreeInfo> getPageTreeInfo(String url, int depth, List<PageTreeInfo> pageTreeInfo) {
		Set<String> updatedProcessedUrls = Optional.ofNullable(links).orElse(new HashSet<String>());
		PageTreeInfo pageTree = new PageTreeInfo();
		if (depth < 0) {
			return pageTreeInfo;
		} else {
			if (!updatedProcessedUrls.contains(url)) {
				try {
					Document document = Jsoup.connect(url).get();
					Elements linksOnPage = document.select("a[href]");
					final Elements images = document.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
					pageTree.setLink(url);
					pageTree.setTitle(document.title());
					pageTree.setImageCount(images.size());
					pageTreeInfo.add(pageTree);
					linksOnPage.forEach(link -> {
						getPageTreeInfo(link.attr("abs:href"), depth - 1, pageTreeInfo);
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return pageTreeInfo;
		}
	}
}
