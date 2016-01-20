/**
@Title: DicUtil.java 
* @Package com.ai.wocloud.system.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author zbc 
* @date 2014年11月26日 下午5:41:43 
* @version V1.0 
**/
package com.ai.wocloud.system.util;

import java.util.HashMap;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.wocloud.bean.SoftwareType;
import com.ai.wocloud.service.interfaces.SoftwareTypeService;

/**
 * @author zhangbc
 *
 */
public class DicUtil {
	
	
	
	
	public static HashMap<String, String> getAllSoftTypeDic()
	{
		HashMap<String,String> map=new HashMap<String,String>();
		  ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/common/applicationContext.xml");  
		SoftwareTypeService softwareTypeService=(SoftwareTypeService)context.getBean("softwareTypeServiceImpl");
		List<SoftwareType> list=softwareTypeService.findAllSoftwareType();
		for(SoftwareType softType:list)
		{
			map.put(softType.getServiceTypeId(), softType.getServiceTypeName());
		}
		return map;
	}
	
}
