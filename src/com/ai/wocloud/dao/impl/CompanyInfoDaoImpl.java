/**
 * Project Name:wo-cloud-dev
 * File Name:CompanyInfoDaoImpl.java
 * Package Name:com.ai.wocloud.companyInfo.dao.impl
 * Date:2014-10-2814:35:51
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.wocloud.bean.CompanyInfo;
import com.ai.wocloud.bean.LoginInfo;
import com.ai.wocloud.dao.interfaces.CompanyInfoDao;

/**
 * ClassName:CompanyInfoDaoImpl <br/>
 * Date:     2014-10-28 14:35:51 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Repository
public class CompanyInfoDaoImpl implements CompanyInfoDao {
    private final String INSERT_COMPANYINFO = "insertCompanyInfo";
    private final String GET_COMPANYINFO_BY_LOGINID = "findCompanyInfoByLoginId";
    private final String PHONE_EXIST_OR_NOT="phoneExistorNot";
    private final String BASIC_INFO_EDIT="basicInfoEdit";
    private final String EP_EXIST_OR_NOT="enterpriseExistorNot";
   
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    
    @Override
    public void insertCompanyInfoDao(CompanyInfo companyInfo) {
        sqlSessionTemplate.insert(INSERT_COMPANYINFO, companyInfo);
    }

    @Override
    public CompanyInfo findCompanyInfoByLoginId(LoginInfo loginInfo) {
        return sqlSessionTemplate.selectOne(GET_COMPANYINFO_BY_LOGINID, loginInfo);
    }

	@Override
	public boolean getUsernameAndPhone(Map<String, String> paramMap) {
		CompanyInfo companyInfo=sqlSessionTemplate.selectOne(PHONE_EXIST_OR_NOT, paramMap);
        if(companyInfo!=null) return true;
        return false;
	}

	@Override
	public void basicInfoEdit(CompanyInfo companyInfo) {
		sqlSessionTemplate.update(BASIC_INFO_EDIT, companyInfo);
	}

	@Override
	public boolean getEnterpriseName(String enterpriseName) {
		List<Object> companyList=sqlSessionTemplate.selectList(EP_EXIST_OR_NOT, enterpriseName);
        if(companyList.size()==0) return true;
        return false;
	}


}
