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
import java.net.URLEncoder;

import org.apache.log4j.Logger;

public class PostJson {
    static Logger logger = Logger.getLogger(PostJson.class);
	
	/**
	 * get 方式发送数据
	 * @param GET_URL
	 * @param data
	 * @throws IOException
	 * @author zhang xufeng
	 */
	 public static String readContentFromGet(String GET_URL,String data) throws IOException {
	        // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
	        String getURL = GET_URL + "?contentData="
	                + URLEncoder.encode(data, "utf-8");
	        URL getUrl = new URL(getURL);
	        // 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
	        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
	        HttpURLConnection connection = (HttpURLConnection) getUrl
	                .openConnection();
	        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
	        // 服务器
	        connection.connect();
	        // 取得输入流，并使用Reader读取
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                connection.getInputStream()));
	        logger.info("Contents of get request");
	        String lines;
	        String result="";
	        while ((lines = reader.readLine()) != null) {
	        	result=new String(lines.getBytes("GBK"), "utf-8");
	        }
	        reader.close();
	        // 断开连接
	        connection.disconnect();
	        logger.info("Contents of get request ends");
	        return result;
	    }
	
	 /**
	  * post方式发送数据
	  * @param POST_URL
	  * @param jsonData
	  * @throws IOException
	  * @author zhang xufeng
	  */
	 public static String readContentFromPost(String POST_URL,String jsonData) {
	        // Post请求的url，与get不同的是不需要带参数
	        URL postUrl = null;
			try {
				postUrl = new URL(POST_URL);
			} catch (MalformedURLException e) {
				logger.error("创建url错误",e);
				return "创建url错误";
			}
	        // 打开连接
	        HttpURLConnection connection = null;
			try {
				connection = (HttpURLConnection) postUrl
				        .openConnection();
			} catch (IOException e) {
				logger.error("打开连接异常",e);
				return "打开连接异常";
			}
	        // 设置是否向connection输出，因为这个是post请求，参数要放在
	        // http正文内，因此需要设为true
	        connection.setDoOutput(true);
	        connection.setDoInput(true);
	        try {
				connection.setRequestMethod("POST");
			} catch (ProtocolException e) {
				logger.error("请求方式错误",e);
				return "请求方式错误";
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
				logger.error("获取连接异常",e);
				return "获取连接异常";
			}
	        byte[] b = null;
			try {
				b = jsonData.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error("字符转换异常",e);
				return "字符转换异常";
			}
	        
	        BufferedOutputStream bos = null;
			try {
				bos = new BufferedOutputStream(connection.getOutputStream());
			} catch (IOException e) {
				logger.error("创输出流异常",e);
				return "创输出流异常";
			}
	        DataOutputStream out = new DataOutputStream(bos);
	       
	        
	        // The URL-encoded contend
	        // 正文，正文内容其实跟get的URL中'?'后的参数字符串一致
	        //String content =  URLEncoder.encode(jsonData, "utf-8");
	        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写道流里面
	        try {
				out.write(b);
				out.flush();
		        out.close(); // flush and close
			} catch (IOException e) {
				logger.error("输出流输出异常",e);
				return "输出流输出异常";
			}
	        
	        BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(
				        connection.getInputStream()));
			} catch (IOException e) {
				logger.error("获取返回信息异常",e);
				return "获取返回信息异常，提交失败！";
			}
	        String line;
	        String result="";
	        logger.info("Contents of get request");
	        try {
				while ((line = reader.readLine()) != null) {
					try {
						result=new String(line.getBytes("GBK"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						logger.error("字符转换异常",e);
						return "字符转换异常！";
					}
					System.out.println(result);
				}
			} catch (IOException e) {
				logger.error("读取返回信息异常",e);
				return "读取返回信息异常！";
			}
	        logger.info("Contents of post request ends");
	        try {
				reader.close();
			} catch (IOException e) {
				logger.error("流关闭异常",e);
				return "流关闭异常";
			}
	        connection.disconnect();
	        return "发送数据成功";
	    }

}
