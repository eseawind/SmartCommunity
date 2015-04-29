package com.smartcommunity.interceptor;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.smartcommunity.cache.ResourceCache;
import com.smartcommunity.util.UTIL;

public class CookieInterceptor extends AbstractInterceptor {

	/**
	 * 拦截 cookie 是否有效
	 */
	private static final long serialVersionUID = 7274638558154706283L;
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// 获取 http 请求与响应
		HttpServletRequest request = org.apache.struts2.ServletActionContext.getRequest();
		HttpServletResponse response = org.apache.struts2.ServletActionContext.getResponse();
//		System.out.println("contextPath " + request.getContextPath() )	; 	// /MyBatis
//		System.out.println("uri " + request.getRequestURI() )	;					// /MyBatis/test/test.action
//		System.out.println("url " + request.getRequestURL() )	;					// http://127.0.0.1:8080/MyBatis/test/test.action
//		System.out.println("servlet path " + request.getServletPath() )	;		// /test/test.action

		/** 对登录请求不予拦截 */
		String servletPath = request.getServletPath();		

		if ( servletPath.indexOf("login") != -1 ){
			return arg0.invoke();
		}
		/** 如果当前的 sessionid 无效 则跳转到登陆页面*/
		if (!request.isRequestedSessionIdValid()) {

			javax.servlet.http.Cookie [] cookies = request.getCookies(); 
			if (cookies == null)
				return UTIL.RESULT_TOLOGIN;
			for (javax.servlet.http.Cookie cookie : cookies) {
		
				if (cookie.getName().equals("JSESSIONID")) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					return UTIL.RESULT_TOLOGIN;
				}
			}
			return UTIL.RESULT_TOLOGIN;
		}
		
		/** 会话有效则判断其是否有权限访问此 action */
//		Set<Integer> resourceSet = (Set<Integer>) request.getSession().getAttribute(UTIL.sessionRoles);

//		if (ResourceCache.isPermit(resourceSet, servletPath))
			return arg0.invoke();

	//	return PERMISION;
	}

}
