package com.wang.model;

import java.io.Serializable;

public class AppointmentModel implements Serializable {
	private String arTYmsg;
	private String arZCmsg;
	private String arId;
	private String arApplyName;
	private String arName;
	private String arMessage;
	private String arUserId;
	private String arTime;
	private String arMeetingRoomId;
	
	
	
	public String getArMeetingRoomId() {
		return arMeetingRoomId;
	}

	public void setArMeetingRoomId(String arMeetingRoomId) {
		this.arMeetingRoomId = arMeetingRoomId;
	}

	public String getArTYmsg() {
		return arTYmsg;
	}

	public void setArTYmsg(String arTYmsg) {
		this.arTYmsg = arTYmsg;
	}

	public String getArZCmsg() {
		return arZCmsg;
	}

	public void setArZCmsg(String arZCmsg) {
		this.arZCmsg = arZCmsg;
	}

	public String getArId() {
		return arId;
	}

	public void setArId(String arId) {
		this.arId = arId;
	}

	public String getArApplyName() {
		return arApplyName;
	}

	public void setArApplyName(String arApplyName) {
		this.arApplyName = arApplyName;
	}

	public String getArName() {
		return arName;
	}

	public void setArName(String arName) {
		this.arName = arName;
	}

	public String getArMessage() {
		return arMessage;
	}

	public void setArMessage(String arMessage) {
		this.arMessage = arMessage;
	}

	public String getArUserId() {
		return arUserId;
	}

	public void setArUserId(String arUserId) {
		this.arUserId = arUserId;
	}

	public String getArTime() {
		return arTime;
	}

	public void setArTime(String arTime) {
		this.arTime = arTime;
	}

}
