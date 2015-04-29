package com.smartcommunity.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;





import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Repair;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.service.IDeviceService;
import com.smartcommunity.service.IRepairService;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;


public class RepairAction extends BaseActionSupport<RepairParams> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IRepairService repairService;
	private IDeviceService deviceService;


	public IRepairService getRepairService() {
		return repairService;
	}
	public void setRepairService(IRepairService repairService) {
		this.repairService = repairService;
	}
	public IDeviceService getDeviceService() {
		return deviceService;
	}
	public void setDeviceService(IDeviceService deviceService) {
		this.deviceService = deviceService;
	}
	/**
	 * 列出报修信息
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String listRepariInfoByPage() throws UnsupportedEncodingException {
		JSONObject jsonObject = null;

				/** 用户查询 */
				jsonObject = repairService.listRepariInfoByPage(parameters.getPageNo(), parameters.getPageSize(),parameters.isFinished(),parameters.getRoomNumberSearchPattern());
	
		inputStream = InputStreamUtil.getInputStream(jsonObject);

		return SUCCESS;
	}
	/**
	 * 列出所有设备
	 * @return
	 */
	public String listDevices(){


		JSONObject jsonObject = deviceService.getDevice(null);

		inputStream = InputStreamUtil.getInputStream(jsonObject); 
		return SUCCESS;
	}

	/**
	 * 提交报修信息
	 * @return
	 * @throws Exception
	 */
	public String submitRepairInfo() throws Exception {
		JSONObject jsonObject = parameters.checkSubmit();
		if (jsonObject != null) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		Integer role = getRole();
		javax.servlet.http.HttpSession httpSession = getHttpSession();
		if (role == UTIL.roleuser) {

				com.smartcommunity.pojo.Roomowner roomowner = (Roomowner) httpSession.getAttribute(com.smartcommunity.util.UTIL.sessionUser);
				parameters.getRepair().setRoomnumber(roomowner.getRoomnumber());
				parameters.getRepair().setUsername(roomowner.getName());
			
		} 
		System.out.println(parameters.getImagesFileName());
			jsonObject = repairService.submitRepairInfo(parameters.getRepairWithParams(),parameters.getImages(),parameters.getImagesFileName(),parameters.getImagesContentType());


		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public String setProcessed() throws UnsupportedEncodingException {
		boolean status;

		net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
		System.out.println("id-->");
		inputStream = new java.io.ByteArrayInputStream(jsonObject.toString().getBytes("utf-8"));
		return SUCCESS;
	}

	public String queryRepairInfo() throws UnsupportedEncodingException {
//		javax.servlet.http.HttpServletRequest httpServletRequest = org.apache.struts2.ServletActionContext.getRequest();
//
//		net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
//		if (!httpServletRequest.isRequestedSessionIdValid() ) {
//			System.out.println("session is invalide");  
//			jsonObject.put("success", false);
//			inputStream = new java.io.ByteArrayInputStream(jsonObject.toString().getBytes());
//			return SUCCESS;
//		}
//		javax.servlet.http.HttpSession httpSession = httpServletRequest.getSession(false);
//		if (httpSession == null) { 		
//			System.out.println("session is null");  
//			jsonObject.put("success", false);
//			inputStream = new java.io.ByteArrayInputStream(jsonObject.toString().getBytes());
//			return SUCCESS;
//		}
//		//httpSession.invalidate();
//		User user = (User) httpSession.getAttribute("user");
////		user.setId(1);
//		if (user == null) {
//			System.out.println("user is null " + httpSession.getId());
//			jsonObject.put("success", false);
//			inputStream = new java.io.ByteArrayInputStream(jsonObject.toString().getBytes());
//			return SUCCESS;
//		}
//		System.out.println("user.getUsername()");
//		System.out.println(user.getUsername());
//	//	user.setId(10);
//		net.sf.json.JSONObject jsonResult = repairInfoService.findUserRepairInfo(user);
//		if (!jsonResult.containsKey("phone")) {
//			jsonResult.put("phone", user.getTelephone());
//		}		
//		if (!jsonResult.containsKey("address")) {
//			jsonResult.put("address", user.getRoom());
//		}
//		System.out.println(jsonResult.toString());
//		//jsonObject.put("success", true);
//		//jsonObject.put("result", jsonResult);
	//	inputStream = new java.io.ByteArrayInputStream(jsonResult.toString().getBytes("utf-8"));
		return SUCCESS;
	}

//	private Boolean validateRepairInfo() {
//		if (repairInfo.getPhone() == null || "".equals(repairInfo.getPhone()) || 
//				repairInfo.getDevicename() == null || "".equals(repairInfo.getDevicename()) ) {
//			return false;
//		}
//		return true;
//	}
	private Boolean fullfillRepairInfo() {
//		System.out.println("fullfillRepairInfo");
//		javax.servlet.http.HttpSession httpSession = 
//				org.apache.struts2.ServletActionContext.getRequest().getSession(false);
//		if (httpSession == null) {
//			System.out.println("session is null");
//			return false;
//		}
//		User user = (User) httpSession.getAttribute("user"); // 获取登陆的用户信息
////		user.setId(2);
////		user.setUsername("username");
////		user.setRoom("room");
//		
//		repairInfo.setAddress(user.getRoom()); 
//		repairInfo.setDate(new Date());
//		repairInfo.setUser(user);
//		repairInfo.setUsername(user.getUsername());
		return true;
		
	}
	/**
	 * 列出设备可能出现的故障信息
	 * @return
	 */
	public String listDeviceFault() {

		JSONObject jsonObject = deviceService.getDeviceFault(parameters.getId());
	
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	/**
	 * 提交用户反馈
	 * @return
	 */
	public String submitFeedback() {
		JSONObject jsonObject = null;
		Integer role = getRole();
		if (role == UTIL.roleuser) {
			Roomowner roomowner = (Roomowner) getHttpSession().getAttribute(com.smartcommunity.util.UTIL.sessionUser);
			parameters.getRepair().setRoomnumber(roomowner.getRoomnumber());
		} 
		jsonObject = repairService.submitFeedback(parameters.getRepairWithParams()) ;


		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public String cancelRepair() {

		javax.servlet.http.HttpSession httpSession = getHttpSession();
		Integer role = getRole();
		Repair repair = new Repair();
		repair.setId(parameters.getId());
		JSONObject jsonObject = null;
		if (role == UTIL.roleuser) {
			Roomowner roomowner = (Roomowner) httpSession.getAttribute(com.smartcommunity.util.UTIL.sessionUser);
			repair.setRoomnumber(roomowner.getRoomnumber());
		} 
			jsonObject = repairService.cancelRepair(repair);
			
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public String setState() {

		Integer role = getRole();
		Repair repair = new Repair();

		JSONObject jsonObject = null;

			jsonObject = repairService.setState(parameters.getId(), parameters.getProcessedstate());

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

}
