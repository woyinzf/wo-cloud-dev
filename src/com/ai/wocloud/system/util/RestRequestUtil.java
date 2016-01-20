/**
 * Project Name:wo-cloud-dev
 * File Name:RestRequestUtil.java
 * Package Name:com.ai.wocloud.system.util
 * Date:2014-10-3017:57:09
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.system.util;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import com.ai.wocloud.system.util.GetInterfacesUrlUtils;

public class RestRequestUtil {
    private static Logger log = Logger.getLogger("RestRequestUtil");
    
    
    /**
     * Function: 短信验证. <br/>
     * Date:     2014-10-30 17:57:09 <br/>
     * @author   zhangyichi
     * @version  
     * @since    JDK 1.6
     * @see      
     */
    public static String getMessageVeryCode(String telNumber) throws Exception {
    	 String REST_URL = GetInterfacesUrlUtils.getInterfacesUrl("restUrl");
        //发送rest请求
        Client client = Client.create();
        URI uri = new URI(REST_URL);
        log.info("-------------"+uri);
        WebResource resource = client.resource(uri);
        JSONObject json = new JSONObject();
        json.put("telNumber", telNumber);
        String param = json.toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(param.getBytes());
        String result = resource.entity(bais).post(String.class);
        log.info("------------"+result);
        //判断请求结果
        JSONObject responseJson = new JSONObject(result);
        String resultCode = responseJson.getString("code");
        //成功返回code 0，失败返回失败原因
        if (resultCode.equals("0")) {
            return resultCode;
        } else {
            return responseJson.getString("msg");
        }
    }
    
    //发送新建客户信息
    public static String sendNewCust(JSONObject json) throws Exception {
    	String CREAT_CUST_URL=GetInterfacesUrlUtils.getInterfacesUrl("creatCustUrl");
        //发送rest请求
        Client client = Client.create();
        URI uri = new URI(CREAT_CUST_URL);
        log.info("-------------"+uri);
        WebResource resource = client.resource(uri);
        String param = json.toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(param.getBytes());
        String result = resource.entity(bais).post(String.class);
        log.info("------------"+result);
        //判断请求结果
        JSONObject responseJson = new JSONObject(result);
        String resultCode = responseJson.getString("code");
        //成功返回code 0，失败返回失败原因
        if (resultCode.equals("0")) {
            return resultCode;
        } else {
            return responseJson.getString("msg");
        }
    }
    
    public static String modifyQuota(String customerId) throws Exception{
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String CREAT_CUST_URL=GetInterfacesUrlUtils.getInterfacesUrl("modifyQuotaUrl");
    	JSONObject json = new JSONObject();
    	json.put("wordorderid", GetInterfacesUrlUtils.getQuota("wordorderid"));
    	json.put("customerid", customerId);
    	json.put("type", GetInterfacesUrlUtils.getQuota("type"));
    	json.put("cpu", GetInterfacesUrlUtils.getQuota("cpu"));
    	json.put("mem", GetInterfacesUrlUtils.getQuota("mem"));
    	json.put("osdisk", GetInterfacesUrlUtils.getQuota("osdisk"));
    	json.put("loadbalance", GetInterfacesUrlUtils.getQuota("loadbalance"));
    	json.put("router", GetInterfacesUrlUtils.getQuota("router"));
    	json.put("ip", GetInterfacesUrlUtils.getQuota("ip"));
    	json.put("bandwidth", GetInterfacesUrlUtils.getQuota("bandwidth"));
    	json.put("clouddesktop", GetInterfacesUrlUtils.getQuota("clouddesktop"));
    	json.put("disksize", GetInterfacesUrlUtils.getQuota("disksize"));
    	json.put("contractid", GetInterfacesUrlUtils.getQuota("contractid"));
    	json.put("operatorid", GetInterfacesUrlUtils.getQuota("operatorid"));
    	json.put("modifyTag", "A");
    	json.put("startdate", format.format(new Date()));
    	Calendar ca = Calendar.getInstance();
    	ca.set(2035, 11, 31);
    	
    	json.put("enddate", format.format(ca.getTime()));
    	 //发送rest请求
        Client client = Client.create();
        URI uri = new URI(CREAT_CUST_URL);
        log.info("--------------配额参数"+json.toString());
        log.info("-------------"+uri);
        WebResource resource = client.resource(uri);
        String param = json.toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(param.getBytes());
        String result = resource.entity(bais).post(String.class);
        log.info("------------"+result);
        //判断请求结果
        JSONObject responseJson = new JSONObject(result);
        String resultCode = responseJson.getString("code");
        //成功返回code 0，失败返回失败原因
        if (resultCode.equals("0")) {
            return resultCode;
        } else {
            return responseJson.getString("msg");
        }
    }
    
    public static String sendApply(JSONObject json) throws Exception{
    	String APPLY_URL=GetInterfacesUrlUtils.getInterfacesUrl("vmUrl");
        //发送rest请求
        Client client = Client.create();
        URI uri = new URI(APPLY_URL);
        log.info("-------------"+uri);
        WebResource resource = client.resource(uri);
        String param = json.toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(param.getBytes());
        String result = resource.entity(bais).post(String.class);
        log.info("------------"+result);
        //判断请求结果
        JSONObject responseJson = new JSONObject(result);
        String resultCode = responseJson.getString("code");
        //成功返回code 0，失败返回失败原因
        if (resultCode.equals("0")) {
            return resultCode;
        } else {
            return responseJson.getString("msg");
        }
    }
    
    public static String getMirror(String userName) throws Exception{
    	String MIRROR_URL=GetInterfacesUrlUtils.getInterfacesUrl("mirrorUrl");
        //发送rest请求
        Client client = Client.create();
        URI uri = new URI(MIRROR_URL);
        log.info("-------------"+uri);
        WebResource resource = client.resource(uri);
        JSONObject json = new JSONObject();
        json.put("customerId", userName);
        String param = json.toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(param.getBytes());
        String result = resource.entity(bais).post(String.class);
        //String result="{'images':[{'imageid':'1234567','imagename':'just'},{'imageid':'1234527','imagename':'g'}]}";
        log.info("------------"+result);       
        return result;
    }
    
    public static String getAccoutTotal(JSONObject json) throws Exception{
    	String ATT_URL=GetInterfacesUrlUtils.getInterfacesUrl("accoutTotalUrl");
        //发送rest请求
        Client client = Client.create();
        URI uri = new URI(ATT_URL);
        log.info("-------------"+uri);
        WebResource resource = client.resource(uri);
        String param = json.toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(param.getBytes());
        String result = resource.entity(bais).post(String.class);
        log.info("------------"+result);
        return result;
    }

    //----------------通用接口调用
	public static String getRest(JSONObject json,String urlName) throws Exception{
		String URL=GetInterfacesUrlUtils.getInterfacesUrl(urlName);
        //发送rest请求
        Client client = Client.create();
        URI uri = new URI(URL);
        log.info("-------------"+uri);
        WebResource resource = client.resource(uri);
        String param = json.toString();
        ByteArrayInputStream bais = new ByteArrayInputStream(param.getBytes("utf-8"));
        String result = resource.entity(bais).post(String.class);
        log.info("------------"+result);
        return result;
	}
    
}
