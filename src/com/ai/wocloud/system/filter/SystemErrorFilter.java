package com.ai.wocloud.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class SystemErrorFilter implements Filter{

	private Logger log = Logger.getLogger(SystemErrorFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("SystemErrorFilter init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} catch (Throwable e) {
			if (e instanceof Error){
				((HttpServletRequest)request).setAttribute("exception", new Exception(e.getMessage()));
			}else if (e instanceof Exception){
				if (e.getCause() != null){
					((HttpServletRequest)request).setAttribute("exception", e.getCause());
					log.debug(e.getMessage());
				}
			}
			//跳转到错误页面
			((HttpServletRequest)request).getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
			return;
		}
		
	}

	@Override
	public void destroy() {
		log.debug("SystemErrorFilter destroy");
	}

}
