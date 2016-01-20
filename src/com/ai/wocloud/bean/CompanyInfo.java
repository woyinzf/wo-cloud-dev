/**
 * Project Name:wo-cloud-dev
 * File Name:CompanyInfo.java
 * Package Name:com.ai.wocloud.companyInfo.bean
 * Date:2014-10-2814:17:11
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.bean;

import java.io.Serializable;

/**
 * ClassName:CompanyInfo <br/>
 * Date:     2014-10-28 14:17:11 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class CompanyInfo implements Serializable{

    /**
     * @since JDK 1.6
     */
    private static final long serialVersionUID = -5202842392854046951L;
    
    private String id;
    //登陆Id
    private String loginId;
    //公司名称
    private String companyName;
    //公司代码
    private String companyCode;
    //公司地址
    private String companyAddress;
    //邮件
    private String email;
    //移动电话
    private String mobilePhone;
    //可用标志 1可用, 0不可用
    private String useful;
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;
    //法人代表图片路径
    private String peasonPicPath;
    //工作执照图片路径
    private String workPicPath;
    
    public String getPeasonPicPath() {
		return peasonPicPath;
	}
	public void setPeasonPicPath(String peasonPicPath) {
		this.peasonPicPath = peasonPicPath;
	}
	public String getWorkPicPath() {
		return workPicPath;
	}
	public void setWorkPicPath(String workPicPath) {
		this.workPicPath = workPicPath;
	}
	public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLoginId() {
        return loginId;
    }
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyCode() {
        return companyCode;
    }
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    public String getCompanyAddress() {
        return companyAddress;
    }
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobilePhone() {
        return mobilePhone;
    }
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    public String getUseful() {
        return useful;
    }
    public void setUseful(String useful) {
        this.useful = useful;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
