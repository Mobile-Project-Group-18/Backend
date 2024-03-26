package com.wang.model;

import java.io.Serializable;

public class ApplyModel implements Serializable{

    	
	private String applyState;
	private String applyHouseUserId;
	private String applyHouseMoney;
	private String applyId;
	private String applyUserId;
	private String applyHouseId;
	private String applyUserName;
	private String applyHouseName;
	private String applyTime;
	private UserModel userMessage;
	
	private HouseModel houseMessage;
	

	public UserModel getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(UserModel userMessage) {
		this.userMessage = userMessage;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getApplyState() {
		return applyState;
	}
	public void setApplyState(String applyState) {
		this.applyState = applyState;
	}
	public String getApplyHouseUserId() {
		return applyHouseUserId;
	}
	public void setApplyHouseUserId(String applyHouseUserId) {
		this.applyHouseUserId = applyHouseUserId;
	}
	public String getApplyHouseMoney() {
		return applyHouseMoney;
	}
	public void setApplyHouseMoney(String applyHouseMoney) {
		this.applyHouseMoney = applyHouseMoney;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public String getApplyHouseId() {
		return applyHouseId;
	}
	public void setApplyHouseId(String applyHouseId) {
		this.applyHouseId = applyHouseId;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public String getApplyHouseName() {
		return applyHouseName;
	}
	public void setApplyHouseName(String applyHouseName) {
		this.applyHouseName = applyHouseName;
	}
	public HouseModel getHouseMessage() {
		return houseMessage;
	}
	public void setHouseMessage(HouseModel houseMessage) {
		this.houseMessage = houseMessage;
	}
	

}
