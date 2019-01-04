package com.jiwei.filter;

import java.io.IOException; 
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import redis.clients.jedis.Jedis;
public class CheckLoginFilter implements Filter {
	protected FilterConfig filterConfig = null;
	private String redirectURL = null;
	private Set<String> notCheckURLList = new HashSet<String>();
	private String sessionKey = null;

	@Override
	public void destroy() {
		notCheckURLList.clear();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域
		if (session.getAttribute("useradmin") != null) {
			 System.out.println("已登录");
			 System.out.println(request.getServletPath());
			String a = request.getServletPath().toString();
			if (a.equals("/login.html")) {
				response.sendRedirect(request.getContextPath() + "/admin/sglbaseShow.jsp");
			} else {
				// filterChain.doFilter(request, response);
				filterChain.doFilter(new XssHttpServletRequestWrapper(request), response);
			}
			return;
		}
		if ((!checkRequestURIIntNotFilterList(request)) && session.getAttribute("useradmin") == null) {
			System.out.println("未登录");
			response.sendRedirect(request.getContextPath() + redirectURL);
			return;
		}
		//filterChain.doFilter(new XssHttpServletRequestWrapper(request), response);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	private boolean checkRequestURIIntNotFilterList(HttpServletRequest request) {
		String uri = request.getServletPath() + (request.getPathInfo() == null ? "" : request.getPathInfo());
		String temp = request.getRequestURI();
		temp = temp.substring(request.getContextPath().length() + 1);
		// System.out.println("是否包括："+uri+";"+notCheckURLList+"=="+notCheckURLList.contains(uri));
		return notCheckURLList.contains(uri);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		redirectURL = filterConfig.getInitParameter("redirectURL");
		sessionKey = filterConfig.getInitParameter("checkSessionKey");
		String notCheckURLListStr = filterConfig.getInitParameter("notCheckURLList");
		if (notCheckURLListStr != null) {
			// System.out.println(notCheckURLListStr);
			String[] params = notCheckURLListStr.split(",");
			for (int i = 0; i < params.length; i++) {
				notCheckURLList.add(params[i].trim());
			}
		}
	}
}
