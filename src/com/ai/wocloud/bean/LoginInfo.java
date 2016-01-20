/**
 * Project Name:wo-cloud-dev
 * File Name:LoginInfo.java
 * Package Name:com.ai.wocloud.loginInfo.bean
 * Date:2014-10-2809:47:16
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.bean;

import java.io.Serializable;

/**
 * ClassName:LoginInfo <br/>
 * Date:     2014-10-28 09:47:16 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class LoginInfo implements Serializable {
    /**
     * @since JDK 1.6
     */
    private static final long serialVersionUID = -2915704018659750308L;
    
    private String id;
    //登录名
    private String loginName;
    //登录密码
    private String loginPassword;
    //登录类型(保留字段)
    private String loginType;
    //有效标志 1有效 0无效
    private String useful;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getLoginPassword() {
        return loginPassword;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    public String getLoginType() {
        return loginType;
    }
    public void setLoginType(String loginType) {
        this.loginType = loginType;
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
