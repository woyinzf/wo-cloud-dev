package com.ai.wocloud.bean;

import java.io.Serializable;

public class ApplyInfo implements Serializable{
	private static final long serialVersionUID = -2915704018659750308L;
	private String applyId; 
	private String softId;
	private String softName;
	private String softType;
	private String softVersion;
	private String applyTime;
	private String applyStatus;
	private String applyType;
	
	
	public String getApplyType() {
        return applyType;
    }
    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }
    public String getSoftId() {
		return softId;
	}
	public void setSoftId(String softId) {
		this.softId = softId;
	}
	public String getSoftName() {
		return softName;
	}
	public void setSoftName(String softName) {
		this.softName = softName;
	}
	public String getSoftType() {
		return softType;
	}
	public void setSoftType(String softType) {
		this.softType = softType;
	}
	public String getSoftVersion() {
		return softVersion;
	}
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getApplyId() {
		return applyId;
	}
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}
	
     
}
