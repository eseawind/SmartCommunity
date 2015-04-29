package com.smartcommunity.action;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Decorate;
import com.smartcommunity.pojo.Decoratefee;
import com.smartcommunity.util.ExceptionUtil;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;

public class DecorateAction extends BaseActionSupport<DecorateParams> {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DecorateAction.class);
	java.io.InputStream inputStream;
	com.smartcommunity.service.IDecorateService decorateService;
	
	public java.io.InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(java.io.InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public com.smartcommunity.service.IDecorateService getDecorateService() {
		return decorateService;
	}
	public void setDecorateService(
			com.smartcommunity.service.IDecorateService decorateService) {
		this.decorateService = decorateService;
	}
	public String submitDecorate() {
		JSONObject jsonObject = new JSONObject();
		if (! parameters.checkDecorate(jsonObject)) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		try {
			jsonObject = decorateService.submitDecorate(parameters.getDecorate());
		} catch (Exception e) {

			logger.error(ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
		
	}
	public String submitDecoratefee() {
		JSONObject jsonObject = new JSONObject();
		if (! parameters.checkDecoratefee(jsonObject)) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		try {
			jsonObject = decorateService.submitDecoratefee(parameters.getDecoratefee());
		} catch (Exception e) {

			logger.error(ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
		
		
	}
	public String listDecorate() {
		JSONObject jsonObject = null;
		try {
			jsonObject = decorateService.listDecorate(parameters.getRoomnumber(),parameters.getPageNo(),parameters.getPageSize());
		} catch (Exception e) {

			logger.error(ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
		
	}
	public String listDecoratefee() {

		JSONObject jsonObject = null;
		try {
			jsonObject = decorateService.listDecoratefee(parameters.getRoomnumber(),parameters.getPageNo(),parameters.getPageSize());
		} catch (Exception e) {

			logger.error(ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
		
	}
}
