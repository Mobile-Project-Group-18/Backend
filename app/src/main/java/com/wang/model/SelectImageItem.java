package com.wang.model;

import java.io.Serializable;
public class SelectImageItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private int sid;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
}
