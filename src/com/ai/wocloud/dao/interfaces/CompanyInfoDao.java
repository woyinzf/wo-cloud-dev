/**
 * Project Name:wo-cloud-dev
 * File Name:CompanyInfoDao.java
 * Package Name:com.ai.wocloud.companyInfo.dao.interfaces
 * Date:2014-10-2814:33:28
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.dao.interfaces;

import java.util.Map;

import com.ai.wocloud.bean.CompanyInfo;
import com.ai.wocloud.bean.LoginInfo;

/**
 * ClassName:CompanyInfoDao <br/>
 * Function: 公司信息 <br/>
 * Date:     2014-10-28 14:33:28 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface CompanyInfoDao {
    /**
     * 
     * insertCompanyInfoDao: <br/>
     * 注册时增加商铺信息
     *
     * @author zhangyichi
     * @param companyInfo
     * @since JDK 1.6
     */
    public abstract void insertCompanyInfoDao(CompanyInfo companyInfo);
    
    /**
     * 
     * findCompanyInfoByLoginId: <br/>
     * 根据登陆ID查询公司信息<br/>
     *
     * @author zhangyichi
     * @param loginInfo
     * @return
     * @since JDK 1.6
     */
    public abstract CompanyInfo findCompanyInfoByLoginId(LoginInfo loginInfo);

	public abstract boolean getUsernameAndPhone(Map<String, String> paramMap);

	public abstract void basicInfoEdit(CompanyInfo companyInfo);

	public abstract boolean getEnterpriseName(String enterpriseName);


}
