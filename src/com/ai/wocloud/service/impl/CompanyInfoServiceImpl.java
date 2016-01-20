/**
 * Project Name:wo-cloud-dev
 * File Name:CompanyInfoServiceImpl.java
 * Package Name:com.ai.wocloud.companyInfo.service.impl
 * Date:2014-10-2814:41:37
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.service.impl;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.wocloud.bean.CompanyInfo;
import com.ai.wocloud.bean.LoginInfo;
import com.ai.wocloud.dao.interfaces.CompanyInfoDao;
import com.ai.wocloud.service.interfaces.CompanyInfoService;
import com.ai.wocloud.system.util.DateUtil;

/**
 * ClassName:CompanyInfoServiceImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2014-10-28 14:41:37 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
    @Autowired
    private CompanyInfoDao companyInfoDao;

    @Override
    public void registerCompanyInfo(CompanyInfo companyInfo, LoginInfo loginInfo) {
        companyInfo.setId(UUID.randomUUID().toString().replace("-", ""));
        companyInfo.setLoginId(loginInfo.getId());
        companyInfo.setCreateTime(DateUtil.nowEn());
        companyInfo.setUseful("1");
        companyInfo.setUpdateTime("");
        companyInfoDao.insertCompanyInfoDao(companyInfo);
    }

    @Override
    public CompanyInfo findCompanyInfoByLoginId(LoginInfo loginInfo) {
        return companyInfoDao.findCompanyInfoByLoginId(loginInfo);
    }

	@Override
	public boolean getUsernameAndPhone(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		return companyInfoDao.getUsernameAndPhone(paramMap);
	}

	@Override
	public void basicInfoEdit(CompanyInfo companyInfo) {
		// TODO Auto-generated method stub
		companyInfoDao.basicInfoEdit(companyInfo);
	}

	@Override
	public boolean getEnterpriseName(String enterpriseName) {
		// TODO Auto-generated method stub
		return companyInfoDao.getEnterpriseName(enterpriseName);
	}

}
