package com.smartcommunity.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ReturnInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ReturnInterceptor");
		// add
//		byte[] b = new byte[100];
//		javax.servlet.http.HttpServletRequest request =  ServletActionContext.getRequest();
//		String tempString = "user.username";
//		
//
//		System.out.println("from http " + tempString + " " + request.getParameter(tempString) );
//		tempString = "user.password";
//		System.out.println("from http " + tempString + " " + request.getParameter(tempString) );
//		request.getInputStream().read(b, 0, 100);
//		System.out.println("from inputstream " + b.toString() );
		// addend
		HttpServletResponse response = (HttpServletResponse) ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		return ai.invoke();
	}

}
