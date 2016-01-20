/**
 * Project Name:wo-cloud-dev
 * File Name:LoginInfoServiceImpl.java
 * Package Name:com.ai.wocloud.loginInfo.service.impl
 * Date:2014-10-2810:15:04
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.service.impl;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.wocloud.bean.LoginInfo;
import com.ai.wocloud.bean.MessageVeryCodeDto;
import com.ai.wocloud.dao.interfaces.CompanyInfoDao;
import com.ai.wocloud.dao.interfaces.LoginInfoDao;
import com.ai.wocloud.service.interfaces.LoginInfoService;
import com.ai.wocloud.system.util.DateUtil;

/**
 * ClassName:LoginInfoServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2014-10-28 10:15:04 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Service
@Transactional
public class LoginInfoServiceImpl implements LoginInfoService {
    @Autowired
    private LoginInfoDao loginInfoDao;
    @Autowired
    private CompanyInfoDao companyInfoDao;
    private Logger logger = Logger.getLogger(LoginInfoServiceImpl.class);

    @Override
    public void insertLoginInfo(LoginInfo loginInfo) {
        loginInfo.setCreateTime(DateUtil.nowEn());
        loginInfo.setId(UUID.randomUUID().toString().replace("-", ""));
        loginInfo.setUseful("1");
        loginInfo.setUpdateTime("");
        loginInfoDao.insertLoginInfo(loginInfo);
    }

    @Override
    public LoginInfo login(LoginInfo loginInfo) {
       try {
           LoginInfo resultInfo = loginInfoDao.login(loginInfo);
           if (resultInfo != null) {
               return resultInfo;
           } else {
               return null;
           }
       } catch (Exception e) {
           String errorMessage = "登录失败";
           logger.error(errorMessage, e);
           return null;
       }
    }

    @Override
    public MessageVeryCodeDto getMessageVeryCode(String phone) {
        return loginInfoDao.getMessageVeryCode(phone);
    }
    
    @Override
    public boolean getUsername(String loginName) {
        return loginInfoDao.getUsername(loginName);
        
    }
    @Override
	public boolean oldPwdVery(LoginInfo loginInfo) {
		// TODO Auto-generated method stub
		return loginInfoDao.oldPwdVery(loginInfo);
	}

	@Override
	public void pwdChange(LoginInfo loginInfo) {
		loginInfoDao.pwdChange(loginInfo);
		
	}

	@Override
	public LoginInfo validationInfo(LoginInfo loginInfo) {
		 try {
	           LoginInfo resultInfo = loginInfoDao.validationInfo(loginInfo);
	           if (resultInfo != null) {
	               return resultInfo;
	           } else {
	               return null;
	           }
	       } catch (Exception e) {
	           String errorMessage = "登录失败";
	           logger.error(errorMessage, e);
	           return null;
	       }
	}

}
