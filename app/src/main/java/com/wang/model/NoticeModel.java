package com.wang.model;

import java.io.Serializable;

public class NoticeModel implements Serializable{
	private String newsTitle;
	private String newsContent;
	private String newsTime;
	
    	
	public String getNewsTitle() {
		return newsTitle;
	}
	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}
	public String getNewsContent() {
		return newsContent;
	}
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	public String getNewsTime() {
		return newsTime;
	}
	public void setNewsTime(String newsTime) {
		this.newsTime = newsTime;
	}


}
