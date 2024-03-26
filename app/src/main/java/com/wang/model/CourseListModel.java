package com.wang.model;

import java.io.Serializable;

public class CourseListModel implements Serializable{

	private String vTitle;
	private String vCreatTime;
	private String vContent;
	private String vFile;
	private String vTime;
	private String vid;

	public String getvTitle() {
		return vTitle;
	}

	public void setvTitle(String vTitle) {
		this.vTitle = vTitle;
	}

	public String getvCreatTime() {
		return vCreatTime;
	}

	public void setvCreatTime(String vCreatTime) {
		this.vCreatTime = vCreatTime;
	}

	public String getvContent() {
		return vContent;
	}

	public void setvContent(String vContent) {
		this.vContent = vContent;
	}

	public String getvFile() {
		return vFile;
	}

	public void setvFile(String vFile) {
		this.vFile = vFile;
	}

	public String getvTime() {
		return vTime;
	}

	public void setvTime(String vTime) {
		this.vTime = vTime;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

}
