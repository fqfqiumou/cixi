package com.jiwei.Interceptor;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.SystemUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jiwei.entity.Useradmin;

public class superadminInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		Useradmin useradmin = (Useradmin) session.getAttribute("useradmin");
		String url = arg0.getRequestURI();
		if (useradmin == null || useradmin.getPower() != 413) {
			String basePath = arg0.getScheme() + "://" + arg0.getServerName() + ":" + arg0.getServerPort();
			//arg1.sendRedirect(basePath + "/ss" + "/login.html");
			arg1.getWriter().printf("不是超级管理员");
			arg1.setHeader("Access-Control-Allow-Origin", "*");
			arg1.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
			arg1.setHeader("Access-Control-Max-Age", "3600");
			arg1.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			arg1.setContentType("text/html");
			arg1.setCharacterEncoding("utf-8");
			arg1.getWriter().flush();
			arg1.getWriter().close();
			System.out.println("不是超级管理员");
			return false;
		} else {
			return true;
		}

	}

}
