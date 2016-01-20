package com.ai.wocloud.system.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StringUtil {
	/**
	 * 文件名加后缀
	 * @param filePathString
	 * @param addString
	 * @return
	 */
	public String addForFilePath(String filePathString,String addString){
		int dotaIndex=filePathString.lastIndexOf(".");
		if(dotaIndex!=-1){
			return filePathString.substring(0,dotaIndex)+addString+filePathString.substring(dotaIndex);
		}else {
			return filePathString+addString;
		}		
	}
	public static String getCapitalize(String srcString,String addString){
		return addString+(srcString.charAt(0)+"").toUpperCase()+srcString.substring(1);
	}
	public static String getNoCapitalize(String srcString,String addString){
		return addString+(srcString.charAt(0)+"").toLowerCase()+srcString.substring(1);
	}
	public static String addWhenUpper(String srcString,String addString){
		StringBuilder returnStringBuilder=new StringBuilder();
		for (int i = 0; i < srcString.length(); i++) {
			char c=srcString.charAt(i);
			if (Character.isUpperCase(c)) {
				returnStringBuilder.append(addString+c);
			}else {
				returnStringBuilder.append(c);
			}
		}
		return returnStringBuilder.toString();
	}
	/**
	 * 获取一个字符串首字母大写的格式
	 * 例如：输入adminInfo将返回AdminInfo
	 * @param srcString
	 * @return
	 */
	public static String getFirstUp(String srcString){
		return (srcString.charAt(0)+"").toUpperCase()+srcString.substring(1);
	}
	/**
	 * 获取一个字符串首字母小写的格式
	 * 例如：输入AdminInfo将返回adminInfo
	 * @param srcString
	 * @return
	 */
	public static String getFirstLower(String srcString){
		return (srcString.charAt(0)+"").toLowerCase()+srcString.substring(1);
	}
	/**
	 * 判断一个字符串是否为空，如果为空返回true,否则返回false.
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str){
		boolean flag = false;
		if(str==null || "".equals(str.trim())){
			flag = true;
		}
		return flag;
	}
	/**
	 * 判断一个字符串是否为空、为null为undefined
	 * @param str
	 * @return
	 */
	public static boolean isHtmlBlank(String str){
		boolean flag = false;
		if(str==null || "".equals(str.trim())||"null".equals(str.trim())||"undefined".equals(str.trim())){
			flag = true;
		}
		return flag;
	}
	/**
	 * 该方法主要用于把key1=value&key2=value2字符串转化成map
	 * @param param
	 * @return
	 */
	public static Map<String, String> transformParam(String param){
		Map<String, String> paramMap=new HashMap<String, String>();
		try {
			if(!isBlank(param)){
				String[] arr=param.split("&");
				for (int i=0;i<arr.length;i++) {
					String[] array=arr[i].split("=");
					if(array.length>0){
						if(array.length==2){
							paramMap.put(java.net.URLDecoder.decode(array[0],   "utf-8"), java.net.URLDecoder.decode(array[1],   "utf-8"));
						}else{
							paramMap.put(java.net.URLDecoder.decode(array[0],   "utf-8"), "");
						}
					}
				}					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramMap;
	}
	
	/**
	 * 转换编码
	 * - 处理乱码问题
	 * @return str为空、转换出错返回null
	 */
	public static String decode(String str, String fromCode, String toCode){
		if(isBlank(str)){
			return null;
		}else{
			try {
				return new String(str.getBytes(fromCode), toCode);
				
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
	}
	
	/**
	 * version截取
	 */
	public static String versionSplit(String str){
		if(!isBlank(str) && str.contains("|")){
			str=str.substring(0, str.indexOf("|"));
		}
		return str;
	}
	
	/**
	 * mirrorName截取
	 */
	public static String mirrorNameSplit(String str){
		if(!isBlank(str) &&str.contains(",")){	
			if(str.contains("|")){
				str=str.substring(str.indexOf(",")+1, str.indexOf("|"));
			}else{				
				str=str.substring(str.lastIndexOf(",")+1);
			}
		}
		
		return str;
	}
	
}
