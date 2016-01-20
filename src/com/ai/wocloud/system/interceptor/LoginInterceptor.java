package com.ai.wocloud.system.interceptor;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ai.wocloud.system.session.SessionInfo;

public class LoginInterceptor implements HandlerInterceptor {
	private Logger log = Logger.getLogger(LoginInterceptor.class);
	/**
	 * controller之前执行
	 */
	@SuppressWarnings("unchecked")
    @Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String url = request.getRequestURI().toString().replace(request.getContextPath(), "");
		log.debug("请求的url：" + url);
		
		//根据session判断用户是否登陆
		if (SessionInfo.isSessionExp(request)){
			Map<String, String[]> properties = request.getParameterMap();
			Set<String> keySet = properties.keySet();
			String[] value ;
			StringBuffer sb = new StringBuffer();
			for (String key : keySet){
				value = properties.get(key);
				for (String v : value){
					sb.append(key + "=" + v + "&");
				}
			}
			request.getSession().setAttribute("backParam", sb.toString());
			request.getSession().setAttribute("backUrl", url);
			response.sendRedirect(request.getContextPath() + "/loginInfoAction/unLogin");
			return false;
		}
		
		return true;
	}

	/**
	 * 生成试图之前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 最后执行，可用于释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
