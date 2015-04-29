package com.smartcommunity.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;

public class PageModelDrivenInterceptor extends AbstractInterceptor {

    protected boolean refreshModelBeforeResult = false;

    public void setRefreshModelBeforeResult(boolean val) {
        this.refreshModelBeforeResult = val;
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
    		// TODO Auto-generated method stub
    		System.out.println("this is my modeldriven interceptor");
    		
    		HttpServletRequest httpServletRequest = ServletActionContext.getRequest();
    		
    		Enumeration<String> paramNames = httpServletRequest.getParameterNames();
    	
    		while (paramNames.hasMoreElements()) {
    			String nameString = paramNames.nextElement();
    			System.out.println(nameString);
    			// 如果需要分页
    			if (nameString.equals("pageSize") || nameString.equals("pageNo")) {
    				paramNames = httpServletRequest.getParameterNames();
    	
        			System.out.println("here" +org.apache.struts2.ServletActionContext.getRequest().getParameter(nameString));
    				return invocation.invoke();
    			}
    		}
    		
    	// 模型驱动
        Object action = invocation.getAction();

        if (action instanceof ModelDriven) {
            ModelDriven modelDriven = (ModelDriven) action;
            ValueStack stack = invocation.getStack();
            Object model = modelDriven.getModel();
            if (model !=  null) {
            	stack.push(model);
            }
            if (refreshModelBeforeResult) {
                invocation.addPreResultListener(new RefreshModelBeforeResult(modelDriven, model));
            }
        }
        return invocation.invoke();
    }

    /**
     * Refreshes the model instance on the value stack, if it has changed
     */
    protected static class RefreshModelBeforeResult implements PreResultListener {
        private Object originalModel = null;
        protected ModelDriven action;


        public RefreshModelBeforeResult(ModelDriven action, Object model) {
            this.originalModel = model;
            this.action = action;
        }

        public void beforeResult(ActionInvocation invocation, String resultCode) {
            ValueStack stack = invocation.getStack();
            CompoundRoot root = stack.getRoot();

            boolean needsRefresh = true;
            Object newModel = action.getModel();

            // Check to see if the new model instance is already on the stack
            for (Object item : root) {
                if (item.equals(newModel)) {
                    needsRefresh = false;
                    break;
                }
            }

            // Add the new model on the stack
            if (needsRefresh) {

                // Clear off the old model instance
                if (originalModel != null) {
                    root.remove(originalModel);
                }
                if (newModel != null) {
                    stack.push(newModel);
                }
            }
        }
    }
}
