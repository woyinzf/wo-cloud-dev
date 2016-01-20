/**
@Title: PrtyConfigDaoImpl.java 
* @Package com.ai.wocloud.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zbc 
* @date 2014年11月24日 下午5:12:51 
* @version V1.0 
**/
package com.ai.wocloud.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ai.wocloud.bean.PrtyConfig;
import com.ai.wocloud.dao.interfaces.PrtyConfigDao;

/**
 * @author zhangbc
 *
 */
@Repository
public class PrtyConfigDaoImpl implements PrtyConfigDao{
	
	 private final String PrtyConfig = "PrtyConfig";
	
	   @Autowired
	    private SqlSessionTemplate sqlSessionTemplate;

	/* (non-Javadoc)
	 * @see com.ai.wocloud.dao.interfaces.PrtyConfigDao#findPrtyConfigByMyid(int)
	 */
	@Override
	public PrtyConfig findPrtyConfigByMyid(int mu_id) {
		return sqlSessionTemplate.selectOne(PrtyConfig,mu_id);
	}

}
