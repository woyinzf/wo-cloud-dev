/**
@Title: PrtyConfigService.java 
* @Package com.ai.wocloud.service.interfaces 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zbc 
* @date 2014年11月24日 下午5:20:48 
* @version V1.0 
**/
package com.ai.wocloud.service.interfaces;

import com.ai.wocloud.bean.PrtyConfig;

/**
 * @author zhangbc
 *
 */
public interface PrtyConfigService {
	
	 public abstract PrtyConfig findPrtyConfigByMyid(int mu_id);

}
