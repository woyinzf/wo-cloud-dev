package com.ai.wocloud.bean;

import java.io.Serializable;

public class ApplyBasic implements Serializable{
	private static final long serialVersionUID = -2915704018659750308L;
	private String applyId;
	private String applyStatus;
	private String applyType;
	
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	
	
}
