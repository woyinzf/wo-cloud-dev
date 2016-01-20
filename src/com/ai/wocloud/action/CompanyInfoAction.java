/**
 * Project Name:wo-cloud-dev
 * File Name:CompanyInfoAction.java
 * Package Name:com.ai.wocloud.companyInfo.action
 * Date:2014-10-2816:42:11
 * Copyright (c) 2014
 *
*/

package com.ai.wocloud.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ai.wocloud.bean.CompanyInfo;
import com.ai.wocloud.bean.LoginInfo;
import com.ai.wocloud.service.interfaces.CompanyInfoService;
import com.ai.wocloud.service.interfaces.LoginInfoService;
import com.ai.wocloud.system.action.BaseAction;
import com.ai.wocloud.system.session.SessionInfo;

import org.apache.log4j.Logger;

/**
 * ClassName:CompanyInfoAction <br/>
 * Date:     2014-10-28 16:42:11 <br/>
 * @author   zhangyichi
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping("/companyInfoAction")
public class CompanyInfoAction extends BaseAction{
	private Logger logger = Logger.getLogger(LoginInfoAction.class);
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private LoginInfoService loginInfoService;
    
    @RequestMapping("/showCompanyInfo")
    public ModelAndView showCompanyInfo(HttpServletRequest request) {
        ModelAndView view  = new ModelAndView("");
        return view;
    }
    
    //-------------------------跳转至密码修改
    @RequestMapping("/toPwdChange")
    public String toPwdChange(HttpServletRequest request){    	
    	return "userInfo/pwdChange";
    }
    
    //---------------------修改密码
    @RequestMapping("/pwdChange")
    public void pwdChange(HttpServletRequest request,HttpServletResponse response){
    	String password = request.getParameter("password");
    	String username=(String) SessionInfo.getLoginInfo(request).getLoginName();
    	LoginInfo loginInfo=new LoginInfo();
    	loginInfo.setLoginName(username);
    	loginInfo.setLoginPassword(password);
    	JSONObject json = new JSONObject();
    	PrintWriter printWriter = null;
    	try {
    		loginInfoService.pwdChange(loginInfo);	
    		json.put("result", "0");
    		printWriter = response.getWriter();
            printWriter.write(json.toString());
            printWriter.flush();
		} catch (Exception e) {
			logger.error("修改失败", e);
		} 
    }
    
    //-------------------------跳转至基本信息
    @RequestMapping("/toBasicInfo")
    public String toBasicInfo(HttpServletRequest request){
    	LoginInfo loginInfo=new LoginInfo();
    	loginInfo.setId(SessionInfo.getCompanyInfo(request).getLoginId());
    	CompanyInfo companyInfo = companyInfoService.findCompanyInfoByLoginId(loginInfo);
    	request.setAttribute("companyInfo", companyInfo);
    	return "userInfo/basicInfo";
    }
    
    //---------------------修改基本信息
    @RequestMapping("/basicInfoEdit")
    public void basicInfoEdit(HttpServletRequest request,HttpServletResponse response){
    	String loginId=SessionInfo.getCompanyInfo(request).getLoginId();
    	String email = request.getParameter("email");
    	String phone=request.getParameter("phone");
    	CompanyInfo companyInfo=new CompanyInfo();
    	companyInfo.setLoginId(loginId);
    	companyInfo.setEmail(email);
    	companyInfo.setMobilePhone(phone);
    	JSONObject json = new JSONObject();
    	PrintWriter printWriter = null;
    	try {
    		companyInfoService.basicInfoEdit(companyInfo);
    		json.put("result", "0");
    		printWriter = response.getWriter();
            printWriter.write(json.toString());
            printWriter.flush();
		} catch (Exception e) {
			logger.error("修改失败", e);
		} 
    }
}
