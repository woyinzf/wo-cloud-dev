/**
 * Project Name:wo-cloud-dev
 * File Name:CompanyInfoService.java
 * Package Name:com.ai.wocloud.companyInfo.service.interfaces
 * Date:2014-10-2814:40:13
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.service.interfaces;

import java.util.Map;

import com.ai.wocloud.bean.CompanyInfo;
import com.ai.wocloud.bean.LoginInfo;

/**
 * ClassName:CompanyInfoService <br/>
 * Function:  <br/>
 * Date:     2014-10-28 14:40:13 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface CompanyInfoService {
    /**
     * 
     * registerCompanyInfo: <br/>
     * 注册公司信息<br/>
     *
     * @author zhangyichi
     * @param companyInfo
     * @param loginInfo
     * @since JDK 1.6
     */
    public abstract void registerCompanyInfo(CompanyInfo companyInfo, LoginInfo loginInfo);
    
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
