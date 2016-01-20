package com.ai.wocloud.system.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class JspFilter implements Filter { 
	private Logger log = Logger.getLogger(SystemErrorFilter.class);
	
    public void doFilter(ServletRequest request, 
    		ServletResponse response, FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;  
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;  
        String url = httpServletRequest.getRequestURI();  
        if(url != null && url.endsWith(".jsp")) {  
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());  
            return;  
        } 
        chain.doFilter(request, response);

    }    
    @Override  
    public void destroy() {  
    	log.debug("JspFilter destroy");
   }   
   @Override  
    public void init(FilterConfig arg0) throws ServletException {  
	    log.debug("JspFilter init");
    }  
  }  

