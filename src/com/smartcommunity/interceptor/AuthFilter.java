package com.smartcommunity.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.smartcommunity.util.UTIL;

/**
 * 可以过滤所有资源
 * @author yang
 *
 */
public class AuthFilter implements Filter {


	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		arg2.doFilter(arg0, arg1);
		return;
//		boolean isFolder = false;
//		javax.servlet.http.HttpServletRequest request = 
//				(javax.servlet.http.HttpServletRequest) arg0;
//		javax.servlet.http.HttpServletResponse response = 
//				(javax.servlet.http.HttpServletResponse) arg1;
//		
//		String currUrl = request.getServletPath();
//		
////		if (currUrl.equals("/SmartProperty/login.html")) {
////			arg2.doFilter(arg0, arg1);
////			return;
////		}
//
//		if (!request.isRequestedSessionIdValid()) {
//
//			javax.servlet.http.Cookie [] cookies = request.getCookies(); 
//			if (cookies == null)
//				return ;
//			for (javax.servlet.http.Cookie cookie : cookies) {
//		
//				if (cookie.getName().equals("JSESSIONID")) {
//					cookie.setMaxAge(0);
//					response.addCookie(cookie);
//					return ;
//				}
//			}
//			return ;
//		}
//
//		 arg2.doFilter(arg0, arg1);
//		 return;
//		if (currUrl.endsWith("/")) {
//			currUrl = currUrl.substring(0,currUrl.length() -1);
//			isFolder = true;
//		}
//		if (currUrl.indexOf(".") == -1) {
//			java.io.InputStream inputStream = getClass().
//					getResourceAsStream("/config/page.properties");
//
//			java.util.Properties properties = new java.util.Properties();
//			properties.load(inputStream);
//			String dispatchUrl = properties.getProperty(currUrl);
//
//			System.out.println("dispatch url " + dispatchUrl);
//			if (dispatchUrl != null && !"".equals(dispatchUrl)) {
//				if (isFolder) {
//					response.sendRedirect(request.getContextPath() + dispatchUrl);
//				} else {
//					
//					request.getRequestDispatcher(dispatchUrl).
//					forward(arg0, arg1);
//					System.out.println("after");
//				}
//			}else {
//				arg2.doFilter(arg0, arg1);
//			}
//		} else {
//			System.out.println("......");
//			arg2.doFilter(arg0, arg1);
//		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
