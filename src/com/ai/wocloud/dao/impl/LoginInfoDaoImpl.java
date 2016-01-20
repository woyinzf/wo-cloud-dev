/**
 * Project Name:wo-cloud-dev
 * File Name:LoginInfoDaoImpl.java
 * Package Name:com.ai.wocloud.loginInfo.dao.impl
 * Date:2014-10-2810:09:02
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.wocloud.bean.LoginInfo;
import com.ai.wocloud.bean.MessageVeryCodeDto;
import com.ai.wocloud.dao.interfaces.LoginInfoDao;

/**
 * ClassName:LoginInfoDaoImpl <br/>
 * Date:     2014-10-28 10:09:02 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Repository
public class LoginInfoDaoImpl implements LoginInfoDao {
    private final String INSERT_LOGININFO = "insertLoginInfo";
    private final String LOGIN = "login";
    private final String validationInfo = "validationInfo";
    private final String GET_MESSAGE_VERY_CODE = "getMessageVeryCode";
    private final String USEREXISTORNOT = "userexistornot";
    private final String OLD_PWD_VERY="oldPwdVery";
    private final String PWD_CHANGE="pwdChange";
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void insertLoginInfo(LoginInfo loginInfo) {
        sqlSessionTemplate.insert(INSERT_LOGININFO, loginInfo);
    }

    @Override
    public LoginInfo login(LoginInfo loginInfo) {
        return sqlSessionTemplate.selectOne(LOGIN, loginInfo);
    }

    @Override
    public MessageVeryCodeDto getMessageVeryCode(String phone) {
        return sqlSessionTemplate.selectOne(GET_MESSAGE_VERY_CODE, phone);
    }
    
    @Override
    public boolean getUsername(String loginName) {
        LoginInfo loginInfo=sqlSessionTemplate.selectOne(USEREXISTORNOT, loginName);
        if(loginInfo!=null) return false;
        return true;
    }

	@Override
	public boolean oldPwdVery(LoginInfo loginInfo) {
		LoginInfo loginInfoRe=sqlSessionTemplate.selectOne(OLD_PWD_VERY,loginInfo);
        if(loginInfoRe ==null) return false;
        return true;
	}

	@Override
	public void pwdChange(LoginInfo loginInfo) {
		sqlSessionTemplate.update(PWD_CHANGE, loginInfo);
	}

	@Override
	public LoginInfo validationInfo(LoginInfo loginInfo) {
		 return sqlSessionTemplate.selectOne(validationInfo, loginInfo);
	}

}
