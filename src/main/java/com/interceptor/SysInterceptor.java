package com.interceptor;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



/**
 * 页面权限拦截器
 * @author hp
 */
public class SysInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(SysInterceptor.class);
	
//	//拦截方法 当页面访问 /sys/** 时拦截
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws IOException {
//		logger.debug("SysInterceptor preHandle");
//		 HttpSession session = request.getSession();
//		 User user = (User)session.getAttribute("user");
//		if(user==null) {
//			//重定向登录页面
//			response.sendRedirect(request.getContextPath()+"/login.html");
//			return false;
//		}
//		return true;
//	}
}
