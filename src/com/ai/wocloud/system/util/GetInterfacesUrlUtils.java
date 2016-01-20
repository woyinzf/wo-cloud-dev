package com.ai.wocloud.system.util;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.ai.wocloud.action.LoginInfoAction;
import com.asiainfo.aiox.common.util.UtilProperties;


public class GetInterfacesUrlUtils {
	
	
	public static String getInterfacesUrl(String key) {
		Logger logger = Logger.getLogger(LoginInfoAction.class);
		Properties prop = new Properties();
		try{
		prop = UtilProperties.getProperties("common/interfaces.properties");
		}catch(Exception e){
			logger.error("interfaces.properties文件未找到");
			return "interfaces.properties文件未找到,或者interfaces.properties文件中未设置相应胡key值";
		}
		return prop.getProperty(key).trim();
	}
	
	public static String getQuota(String key) {
		Logger logger = Logger.getLogger(LoginInfoAction.class);
		Properties prop = new Properties();
		try{
		prop = UtilProperties.getProperties("common/quota.properties");
		}catch(Exception e){
			logger.error("quota.properties文件未找到");
			return "quota.properties文件未找到,或者quota.properties文件中未设置相应胡key值";
		}
		return prop.getProperty(key).trim();
	}
}
