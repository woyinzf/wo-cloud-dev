package com.ai.wocloud.system.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.ai.wocloud.action.LoginInfoAction;

public class QueryOsData {
	
	/**
	 * get 方式发送数据
	 * @param GET_URL
	 * @param data
	 * @throws IOException
	 * @author zhang xufeng
	 */
	 public static String readContentFromGet(String GET_URL){
		 Logger logger = Logger.getLogger(LoginInfoAction.class);
	        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
	        String getURL = GET_URL;
	        URL getUrl = null;
			try {
				getUrl = new URL(getURL);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("创建url错误",e);
			}
	        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
	        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
	        HttpURLConnection connection = null;
			try {
				connection = (HttpURLConnection) getUrl.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("打开连接失败！",e);
			}
	        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
	        // 服务器
	        try {
				connection.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("连接请求失败",e);
			}
	        // 取得输入流，并使用Reader读取
	        BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
				        connection.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("无法获取返回信息",e);
			}
	        System.out.println("=============================");
	        System.out.println("Contents of get request");
	        System.out.println("=============================");
	        String lines;
	        String result = "";
	    
				try {
					while ((lines = reader.readLine()) != null) {
						//byte[] b = lines.getBytes("UTF-8");
						result=new String(lines.getBytes("GBK"), "utf-8");
					}
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					logger.error("读取返回结果字符编码错误！",e1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					logger.error("读取返回信息异常!",e1);
				}
			
	        try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("流关闭异常！",e);
			}
	        // 断开连接
	        connection.disconnect();
	        System.out.println("=============================");
	        System.out.println("Contents of get request ends");
	        System.out.println("=============================");
	        return result;
	    }
	
	 /**
	  * post方式发送数据
	  * @param POST_URL
	  * @param jsonData
	  * @throws IOException
	  * @author zhang xufeng
	  */
	 public static String readContentFromPost(String POST_URL) {
		 Logger logger = Logger.getLogger(LoginInfoAction.class);
		 URL postUrl=null;
		try {
			postUrl = new URL(POST_URL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("创建url失败！",e);
		}
	        // 打开连接
	        HttpURLConnection connection = null;
			try {
				connection = (HttpURLConnection) postUrl
				        .openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("openConnection异常！",e);
			}
	        // 设置是否向connection输出，因为这个是post请求，参数要放在
	        // http正文内，因此需要设为true
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        try {
				connection.setRequestMethod("POST");
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("请求类型异常！",e);
			}
	        // Post 请求不能使用缓存
	        connection.setUseCaches(false);
	        // URLConnection.setFollowRedirects是static函数，作用于所有的URLConnection对象。
	        // connection.setFollowRedirects(true);

	        // URLConnection.setInstanceFollowRedirects是成员函数，仅作用于当前函数
	        connection.setInstanceFollowRedirects(true);
	        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
	        // 意思是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
	        // 进行编码
	        connection.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded; charset=UTF-8");
	        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
	        // 要注意的是connection.getOutputStream会隐含的进行connect。
	        try {
				connection.connect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("获取连接异常！",e);
			}
	        
	        BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(connection.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("创建输出流异常！",e);
			}
	        DataOutputStream out = new DataOutputStream(bos);
	       
	        
	        // The URL-encoded contend
	        // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
	        //String content =  URLEncoder.encode(jsonData, "utf-8");
	        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
	        try {
				out.write(null);
				out.flush();
			    out.close(); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("输出异常！",e);
			}
	       // flush and close
	        BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
				        connection.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("获取返回信息失败！",e);
			}
	        String line;
	        String result="";
	        System.out.println("=============================");
	        System.out.println("Contents of post request");
	        System.out.println("=============================");
	        try {
				while ((line = reader.readLine()) != null) {
					result=new String(line.getBytes("GBK"), "utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("返回信息字符编码异常！",e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("读取返回信息异常！",e);
			}
	        System.out.println("=============================");
	        System.out.println("Contents of post request ends");
	        System.out.println("=============================");
	        try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("流关闭异常！",e);
			}
	        connection.disconnect();
	        return result;
	    }

}
