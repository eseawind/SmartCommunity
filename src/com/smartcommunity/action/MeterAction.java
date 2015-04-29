package com.smartcommunity.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.smartcommunity.pojo.Meter;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.service.IMeterService;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;

public class MeterAction extends BaseActionSupport<MeterParams> {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(MeterAction.class);
	private IMeterService meterService;

	/**
	 * 提交抄表信息
	 * 
	 * @return
	 */
	public String submitMeterInfo() {
		JSONObject jsonObject = new JSONObject();
		// 检查参数是否正确
		if (!parameters.checkParameters(jsonObject)) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		Integer role = getRole();
		List<Meter> meters = parameters.getMeterInfo();

//		if (role == com.smartcommunity.util.UTIL.roleuser) {
//			com.smartcommunity.pojo.Roomowner roomowner = (Roomowner) getHttpSession()
//					.getAttribute(com.smartcommunity.util.UTIL.sessionUser);
//
//			for (Meter meter : meters) {
//				meter.setRoomnumber(roomowner.getRoomnumber());
//			}
//		} 

			try {
				jsonObject = meterService.submitMeterInfo(meters);
			} catch (DuplicateKeyException e) {
				// TODO Auto-generated catch block
				jsonObject = JSONUtil.getJsonObject(false);
				JSONUtil.putCause(jsonObject, "输入水电表数据不能少于上次输入值");
			}


		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	public String listMeterInfo() {
		JSONObject jsonObject = null;
		Integer role = getRole();
//		if (role == com.smartcommunity.util.UTIL.roleuser) {
//			Roomowner roomowner = (Roomowner) getHttpSession().getAttribute(com.smartcommunity.util.UTIL.sessionUser);
//			if (roomowner.getRoomnumber() != null) {
//
//				jsonObject = meterService.listMeterInfo(parameters.getMetertype(),
//						roomowner.getRoomnumber(), parameters.getPageNo(),
//						parameters.getPageSize());
//			}
//		} else if (role == com.smartcommunity.util.UTIL.rolemanager){

			jsonObject = meterService.listMeterInfo(parameters.getMetertype(),
					parameters.getRoomnumberSearchPattern(), parameters.getPageNo(),
					parameters.getPageSize());
	//	}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	public String paying() {
		org.apache.struts2.ServletActionContext.getRequest().getParameter(
				"pageNo");
		JSONObject jsonObject = new JSONObject();
		if (!parameters.checkPament(jsonObject)) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
		}
		System.out.println("" + parameters.getId() + parameters.getAmount());
		try {
			jsonObject = meterService.paying(parameters.getId(),
					parameters.getAmount());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(com.smartcommunity.util.ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	public IMeterService getMeterService() {
		return meterService;
	}

	public void setMeterService(IMeterService meterService) {
		this.meterService = meterService;
	}
	
	public String getAccount() {
		JSONObject jsonObject = meterService.getAccount(parameters.getRoomnumberSearchPattern());
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

}
