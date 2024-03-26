package com.wang.model;

import java.io.Serializable;

/**
 * 
 * @author WangKui
 * 
 */
public class UserModel implements Serializable{

	private String uid;
	private String utime;
	private String uphone;
	private String upswd;
	private String uname;
	private String utype;
	private String ostate;
	private String oid;
	private String adminState;
	private String isVip;
	
	private String applyTypeId;
	
	private String applyTypeName;
	
	
	public String getApplyTypeName() {
		return applyTypeName;
	}

	public void setApplyTypeName(String applyTypeName) {
		this.applyTypeName = applyTypeName;
	}

	public String getApplyTypeId() {
		return applyTypeId;
	}

	public void setApplyTypeId(String applyTypeId) {
		this.applyTypeId = applyTypeId;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

	public String getAdminState() {
		return adminState;
	}

	public void setAdminState(String adminState) {
		this.adminState = adminState;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getOstate() {
		return ostate;
	}

	public void setOstate(String ostate) {
		this.ostate = ostate;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUtime() {
		return utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public String getUpswd() {
		return upswd;
	}

	public void setUpswd(String upswd) {
		this.upswd = upswd;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

}
