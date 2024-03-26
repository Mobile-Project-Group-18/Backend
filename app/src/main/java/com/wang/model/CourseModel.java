package com.wang.model;

import java.io.Serializable;

public class CourseModel implements Serializable{

	private String cTeacherId;
	private String cTeacherName;
	private String cCreateTime;
	private String cid;
	private String cname;
	private String signState;
	
	

	public String getSignState() {
		return signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}

	public String getcTeacherId() {
		return cTeacherId;
	}

	public void setcTeacherId(String cTeacherId) {
		this.cTeacherId = cTeacherId;
	}

	public String getcTeacherName() {
		return cTeacherName;
	}

	public void setcTeacherName(String cTeacherName) {
		this.cTeacherName = cTeacherName;
	}

	public String getcCreateTime() {
		return cCreateTime;
	}

	public void setcCreateTime(String cCreateTime) {
		this.cCreateTime = cCreateTime;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

}
