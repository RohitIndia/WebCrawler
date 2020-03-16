package com.app.webcrawler.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PageTree {
	private int totalImages;
	private int totalLinks;
	private List<PageTreeInfo> details;

	
}
