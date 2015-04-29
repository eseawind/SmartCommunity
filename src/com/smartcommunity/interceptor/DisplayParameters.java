package com.smartcommunity.interceptor;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class DisplayParameters extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		javax.servlet.http.HttpServletRequest request = ServletActionContext.getRequest();
	     java.util.Enumeration<String> params =request.getParameterNames() ;
	      while (params.hasMoreElements()) {
	    	  String key = params.nextElement();
	    	  System.out.println(key + "\t--->\t" + request.getParameter(key));
	      }

        return arg0.invoke();
	}

}
