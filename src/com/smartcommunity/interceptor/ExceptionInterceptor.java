package com.smartcommunity.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.smartcommunity.util.ExceptionUtil;

public class ExceptionInterceptor extends AbstractInterceptor {

	 
    protected static final Logger LOG = LoggerFactory.getLogger(ExceptionInterceptor.class);

    protected Logger categoryLogger;
    protected boolean logEnabled = true;
    protected String logCategory;
    protected String logLevel = "error";
    

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    public String getLogCategory() {
		return logCategory;
	}

	public void setLogCategory(String logCatgory) {
		this.logCategory = logCatgory;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result;

        
        try {
            result = invocation.invoke();
        } catch (Exception e) {

            if (isLogEnabled()) {
                handleLogging(e);
            }
            List<ExceptionMappingConfig> exceptionMappings = invocation.getProxy().getConfig().getExceptionMappings();
            ExceptionMappingConfig mappingConfig = this.findMappingFromExceptions(exceptionMappings, e);
            if (mappingConfig != null && mappingConfig.getResult()!=null) {
                Map parameterMap = mappingConfig.getParams();
                // create a mutable HashMap since some interceptors will remove parameters, and parameterMap is immutable
                invocation.getInvocationContext().setParameters(new HashMap<String, Object>(parameterMap));
                result = mappingConfig.getResult();
                System.out.println("result " + result + parameterMap);
                publishException(invocation, new ExceptionHolder(e));
            } else {
                throw e;
            }
        }
        return result;
    }

    /**
     * Handles the logging of the exception.
     * 
     * @param e  the exception to log.
     */
    protected void handleLogging(Exception e) {
    	if (logCategory != null) {
        	if (categoryLogger == null) {
        		// init category logger
        		categoryLogger = LoggerFactory.getLogger(logCategory);
        	}
        	doLog(categoryLogger, e);
    	} else {
    		doLog(LOG, e);
    	}
    }
    
    /**
     * Performs the actual logging.
     * 
     * @param logger  the provided logger to use.
     * @param e  the exception to log.
     */
    protected void doLog(Logger logger, Exception e) {
    	if (logLevel == null) {
    		logger.debug(ExceptionUtil.getStackTrack(e));
    		return;
    	}
    	
    	if ("trace".equalsIgnoreCase(logLevel)) {
    		logger.trace(ExceptionUtil.getStackTrack(e));
    	} else if ("debug".equalsIgnoreCase(logLevel)) {
    		logger.debug(ExceptionUtil.getStackTrack(e));
    	} else if ("info".equalsIgnoreCase(logLevel)) {
    		logger.info(ExceptionUtil.getStackTrack(e));
    	} else if ("warn".equalsIgnoreCase(logLevel)) {
    		logger.warn(ExceptionUtil.getStackTrack(e));
    	} else if ("error".equalsIgnoreCase(logLevel)) {
    		logger.error(ExceptionUtil.getStackTrack(e));
    	} else {
    		throw new IllegalArgumentException("LogLevel [" + logLevel + "] is not supported");
    	}
    }

    /**
     * Try to find appropriate {@link ExceptionMappingConfig} based on provided Throwable
     *
     * @param exceptionMappings list of defined exception mappings
     * @param t caught exception
     * @return appropriate mapping or null
     */
    protected ExceptionMappingConfig findMappingFromExceptions(List<ExceptionMappingConfig> exceptionMappings, Throwable t) {
    	ExceptionMappingConfig config = null;
        // Check for specific exception mappings.
        if (exceptionMappings != null) {
            int deepest = Integer.MAX_VALUE;
            for (Object exceptionMapping : exceptionMappings) {
                ExceptionMappingConfig exceptionMappingConfig = (ExceptionMappingConfig) exceptionMapping;
                int depth = getDepth(exceptionMappingConfig.getExceptionClassName(), t);
                if (depth >= 0 && depth < deepest) {
                    deepest = depth;
                    config = exceptionMappingConfig;
                }
            }
        }
        return config;
    }

    /**
     * Return the depth to the superclass matching. 0 means ex matches exactly. Returns -1 if there's no match.
     * Otherwise, returns depth. Lowest depth wins.
     *
     * @param exceptionMapping  the mapping classname
     * @param t  the cause
     * @return the depth, if not found -1 is returned.
     */
    public int getDepth(String exceptionMapping, Throwable t) {
        return getDepth(exceptionMapping, t.getClass(), 0);
    }

    private int getDepth(String exceptionMapping, Class exceptionClass, int depth) {
        if (exceptionClass.getName().contains(exceptionMapping)) {
            // Found it!
            return depth;
        }
        // If we've gone as far as we can go and haven't found it...
        if (exceptionClass.equals(Throwable.class)) {
            return -1;
        }
        return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
    }

    /**
     * Default implementation to handle ExceptionHolder publishing. Pushes given ExceptionHolder on the stack.
     * Subclasses may override this to customize publishing.
     *
     * @param invocation The invocation to publish Exception for.
     * @param exceptionHolder The exceptionHolder wrapping the Exception to publish.
     */
    protected void publishException(ActionInvocation invocation, ExceptionHolder exceptionHolder) {
        invocation.getStack().push(exceptionHolder);
    }

}
