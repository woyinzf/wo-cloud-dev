package com.ai.wocloud.system.util;

import java.io.File;

import org.apache.log4j.Logger;

import com.ai.wocloud.action.LoginInfoAction;

public class GetFileName {
	
	
	public static String getFileName(String dir){
		String separator=File.separator;
		String fileName="";
		if(dir == null || "".equals(dir) || dir.indexOf("/") < 0 || dir.indexOf(separator) < 0){
			return dir;
		}else{
			int amp = dir.lastIndexOf(separator);
			if( amp>= 0){
				fileName = dir.substring(amp+1, dir.length()-amp);
			}else{
				int temp = dir.lastIndexOf("/");
				fileName = dir.substring(temp, dir.length()-temp);
				
			}
			return fileName;
		}
		
	}
	public static String replaceName(String str){
		Logger logger = Logger.getLogger(LoginInfoAction.class);
		File tempFile =new File( str.trim());  
		  logger.info(tempFile);
		  System.out.println(tempFile);
        String fileName = tempFile.getName();  
        System.out.println(fileName);
        logger.error(fileName);
		return fileName;
		
	}
	
	
}
