package com.ai.wocloud.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.wocloud.bean.User;
import com.ai.wocloud.service.interfaces.UserService;
import com.ai.wocloud.system.action.BaseAction;

@Transactional(rollbackFor = Exception.class)
@Controller
@RequestMapping(value = "/login")
public class UserAction extends BaseAction {
	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(UserAction.class);

	@RequestMapping(value = "/check")
	public void check(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user = userService.login(user);
			if(user==null){
				this.responseFailed(response, "登陆失败", null);
			}else{
				this.responseSuccess(response, "登陆成功！", null);
			}
		} catch (Exception e) {
			logger.error("登陆失败", e);
			this.responseFailed(response, "登陆失败", null);
		}
	}
	
	@RequestMapping(value="/toHome")
	public String toHome(HttpServletRequest request, HttpServletResponse response){
		String userName=request.getParameter("userName");
		request.setAttribute("userName", userName);
		return "home/toHome";
	}
}
