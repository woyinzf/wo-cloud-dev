/**
 * Project Name:wo-cloud-dev
 * File Name:LoginInfoDao.java
 * Package Name:com.ai.wocloud.loginInfo.dao.interfaces
 * Date:2014-10-2809:58:39
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.dao.interfaces;

import com.ai.wocloud.bean.LoginInfo;
import com.ai.wocloud.bean.MessageVeryCodeDto;

/**
 * ClassName:LoginInfoDao <br/>
 * Function: 登录信息 <br/>
 * Date:     2014-10-28 09:58:39 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface LoginInfoDao {
    /**
     * 
     * insertLoginInfo: <br/>
     * 注册用户<br/>
     *
     * @author zhangyichi
     * @param loginInfo
     * @since JDK 1.6
     */
    public abstract void insertLoginInfo(LoginInfo loginInfo);
    /**
     * 
     * login: <br/>
     * 登陆系统认证<br/>
     *
     * @author zhangyichi
     * @param loginInfo
     * @return
     * @since JDK 1.6
     */
    public abstract LoginInfo login(LoginInfo loginInfo);
    //验证
    public abstract LoginInfo validationInfo(LoginInfo loginInfo);
    
    public abstract boolean getUsername(String loginName);
    /**
     * 
     * getMessageVeryCode: <br/>
     * 获取短信验证码<br/>
     *
     * @author zhangyichi
     * @param phone
     * @return
     * @since JDK 1.6
     */
    public abstract MessageVeryCodeDto getMessageVeryCode(String phone);
    
	public abstract boolean oldPwdVery(LoginInfo loginInfo);
	public abstract void pwdChange(LoginInfo loginInfo);
}
