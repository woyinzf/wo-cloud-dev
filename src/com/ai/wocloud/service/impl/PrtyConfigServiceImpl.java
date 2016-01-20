/**
@Title: PrtyConfigServiceImpl.java 
* @Package com.ai.wocloud.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zbc 
* @date 2014年11月24日 下午5:21:17 
* @version V1.0 
**/
package com.ai.wocloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ai.wocloud.bean.PrtyConfig;
import com.ai.wocloud.dao.interfaces.PrtyConfigDao;
import com.ai.wocloud.service.interfaces.PrtyConfigService;

/**
 * @author zhangbc
 *
 */
@Service
@Transactional
public class PrtyConfigServiceImpl implements PrtyConfigService{

	 @Autowired
	    private PrtyConfigDao prtyConfigDao;
	
	/* (non-Javadoc)
	 * @see com.ai.wocloud.service.interfaces.PrtyConfigService#findPrtyConfigByMyid(int)
	 */
	@Override
	public PrtyConfig findPrtyConfigByMyid(int mu_id) {
		return prtyConfigDao.findPrtyConfigByMyid(mu_id);
	}

}
