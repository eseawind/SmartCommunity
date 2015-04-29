package com.smartcommunity.action;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.smartcommunity.util.ExceptionUtil;
import com.smartcommunity.util.JSONUtil;

public class TongYongBaseAction<T> extends ActionSupport implements ModelDriven<T>,
ApplicationContextAware{
	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;

	protected InputStream inputStream ; 	// 用于返回数据
	/**
     * 声明的参数类
     * <br/><em>不建议名称修改为param
     * 可能会跟页面param对象冲突,导致通过struts取值机制冲突</em>
     */
    protected T parameters;
    protected com.smartcommunity.service.IBaseService<T> baseService;
    public com.smartcommunity.service.IBaseService<T> getBaseService() {
		return baseService;
	}

	public void setBaseService(
			com.smartcommunity.service.IBaseService<T> baseService) {
		this.baseService = baseService;
	}

	/**
     * spring 上下文
     */
    protected ApplicationContext applicationContext;

    /**
     * 构造参数对象
     */
    public TongYongBaseAction() {
        if(parameters==null){
            try {
            	parameters = getParameterizedTypeClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
	
    private Class<T> getParameterizedTypeClass(){
        // 获取泛型类型
        Type type = getClass().getGenericSuperclass();
        Type[] trueType = ((ParameterizedType) type).getActualTypeArguments();
        return (Class<T>) trueType[0];
    }

    public T getParameters() {
		return parameters;
	}


	public void setParameters(T parameters) {
		this.parameters = parameters;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = arg0;
	}

	@Override
	public T getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
    public String list() {
    	com.alibaba.fastjson.JSONObject jsonObject = null;
    	try {
			jsonObject = baseService.doQuery(parameters);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
		}
    	inputStream = com.smartcommunity.util.InputStreamUtil.getInputStream(jsonObject);
    	return SUCCESS;
    }
}
