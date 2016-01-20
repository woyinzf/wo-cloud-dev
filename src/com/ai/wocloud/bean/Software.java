package com.ai.wocloud.bean;

import java.io.Serializable;



public class Software implements Serializable {
	
	/**
	 * 軟件信息模型
	 * @author zhang xufeng
	 * 
	 */
	private static final long serialVersionUID = 8306706628767892651L;
	private int id;
	private String handleId ; //操作人id
	private String softwareName; //软件名称
	private String softwareKind;// 软件种类
	private String softwareVersions;//软件版本
	private String commandTime;//提交时间
	private String softwareData; // 软件信息数据 json
	private String softwareStatus; //审核状态
	
	private String softwareOffer;
	private String softwareType;
	private String operateSystem;
	private String softwareIntroduce;//软件介绍
	private String softwareImgUrl;//图片url
	private String softwareSpecificationUrl;//软件使用说明书 url
	private String softwareConfigSpecificationUrl; //软件配置说明书 url
	private String woCloudUserName; //沃云商城用户名
	private String ghoName;//镜像名称
	private String softwareSellAuthorizationUrl;//软件销售授权书 url
	private String softwareId;
	private int processType;  //申请单类型
	private String softwareSellProportion ; //分成比例
	
	
	
	public String getWoCloudUserName() {
        return woCloudUserName;
    }
    public void setWoCloudUserName(String woCloudUserName) {
        this.woCloudUserName = woCloudUserName;
    }
    public String getGhoName() {
        return ghoName;
    }
    public void setGhoName(String ghoName) {
        this.ghoName = ghoName;
    }
    public String getSoftwareId() {
		return softwareId;
	}
	public void setSoftwareId(String softwareId) {
		this.softwareId = softwareId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getHandleId() {
		return handleId;
	}
	public void setHandleId(String handleId) {
		this.handleId = handleId;
	}
	public String getSoftwareName() {
		return softwareName;
	}
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}
	public String getSoftwareKind() {
		return softwareKind;
	}
	public void setSoftwareKind(String softwareType) {
		this.softwareKind = softwareType;
	}
	public String getSoftwareVersions() {
		return softwareVersions;
	}
	public void setSoftwareVersions(String softwareVersions) {
		this.softwareVersions = softwareVersions;
	}
	public String getCommandTime() {
		return commandTime;
	}
	public void setCommandTime(String commandTime) {
		this.commandTime = commandTime;
	}
	public String getSoftwareData() {
		return softwareData;
	}
	public void setSoftwareData(String softwareData) {
		this.softwareData = softwareData;
	}
	public String getSoftwareStatus() {
		return softwareStatus;
	}
	public void setSoftwareStatus(String softwareStatus) {
		this.softwareStatus = softwareStatus;
	}
	
	public String getSoftwareOffer() {
		return softwareOffer;
	}
	public void setSoftwareOffer(String softwareOffer) {
		this.softwareOffer = softwareOffer;
	}
	public String getSoftwareType() {
		return softwareType;
	}
	public void setSoftwareType(String softwareType) {
		this.softwareType = softwareType;
	}
	public String getOperateSystem() {
		return operateSystem;
	}
	public void setOperateSystem(String operateSystem) {
		this.operateSystem = operateSystem;
	}
	public String getSoftwareIntroduce() {
		return softwareIntroduce;
	}
	public void setSoftwareIntroduce(String softwareIntroduce) {
		this.softwareIntroduce = softwareIntroduce;
	}
	public String getSoftwareImgUrl() {
		return softwareImgUrl;
	}
	public void setSoftwareImgUrl(String softwareImgUrl) {
		this.softwareImgUrl = softwareImgUrl;
	}
	public String getSoftwareSpecificationUrl() {
		return softwareSpecificationUrl;
	}
	public void setSoftwareSpecificationUrl(String softwareSpecificationUrl) {
		this.softwareSpecificationUrl = softwareSpecificationUrl;
	}
	public String getSoftwareConfigSpecificationUrl() {
		return softwareConfigSpecificationUrl;
	}
	public void setSoftwareConfigSpecificationUrl(
			String softwareConfigSpecificationUrl) {
		this.softwareConfigSpecificationUrl = softwareConfigSpecificationUrl;
	}
	public String getSoftwareSellAuthorizationUrl() {
		return softwareSellAuthorizationUrl;
	}
	public void setSoftwareSellAuthorizationUrl(String softwareSellAuthorizationUrl) {
		this.softwareSellAuthorizationUrl = softwareSellAuthorizationUrl;
	}
	public int getProcessType() {
		return processType;
	}
	public void setProcessType(int processType) {
		this.processType = processType;
	}
	public String getSoftwareSellProportion() {
		return softwareSellProportion;
	}
	public void setSoftwareSellProportion(String softwareSellProportion) {
		this.softwareSellProportion = softwareSellProportion;
	}
	
}
