package com.ai.wocloud.system.session;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.ai.wocloud.bean.CompanyInfo;
import com.ai.wocloud.bean.LoginInfo;

public class SessionInfo {
	public final static String COMPANY_INFO = "COMPANY_INFO";
	public final static String LOGIN_INFO="LOGIN_INFO";
	
	public static CompanyInfo getCompanyInfo(PageContext pageContext) {
	    return (CompanyInfo) pageContext.getSession().getAttribute(COMPANY_INFO);
	}
	
	public static CompanyInfo getCompanyInfo(HttpServletRequest request) {
		return (CompanyInfo) request.getSession().getAttribute(COMPANY_INFO);
	}
	
	public static CompanyInfo getCompanyInfo(HttpSession session){
		return (CompanyInfo)session.getAttribute(COMPANY_INFO);
	}
	
	public static LoginInfo getLoginInfo(PageContext pageContext) {
	    return (LoginInfo) pageContext.getSession().getAttribute(LOGIN_INFO);
	}
	
	public static LoginInfo getLoginInfo(HttpServletRequest request) {
		return (LoginInfo) request.getSession().getAttribute(LOGIN_INFO);
	}
	
	public static LoginInfo getLoginInfo(HttpSession session){
		return (LoginInfo)session.getAttribute(LOGIN_INFO);
	}

	public static boolean isSessionExp(HttpServletRequest request){
	    CompanyInfo companyInfo = getLoginCompanyInfo(request);
		return companyInfo == null;
	}
	
	
	public static void setCompanyInfo(HttpSession session, CompanyInfo vo){
		session.setAttribute(COMPANY_INFO, vo);
	}
	public static void setLoginInfo(HttpSession session, LoginInfo vo){
		session.setAttribute(LOGIN_INFO, vo);
	}
	
	public static CompanyInfo getLoginCompanyInfo(HttpServletRequest request) {
		return (CompanyInfo) request.getSession().getAttribute(COMPANY_INFO);
	}
	
	public static void removeLoginCompanyInfo(HttpServletRequest request) {
	    request.getSession().removeAttribute(COMPANY_INFO);
	    request.getSession().removeAttribute(LOGIN_INFO);
	}
}
