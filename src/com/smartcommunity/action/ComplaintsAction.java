package com.smartcommunity.action;

import java.io.File;
import java.io.InputStream;
import java.util.List;








import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.smartcommunity.pojo.Complaints;
import com.smartcommunity.pojo.Repair;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.service.IComplaintsService;
import com.smartcommunity.service.IDeviceService;
import com.smartcommunity.service.IRepairService;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;

public class ComplaintsAction extends BaseActionSupport<ComplaintsParams>{

	private final String roomnumberPattern = "[0-9]-[0-9]-[0-9]{3}";
	private IComplaintsService complaintsService;
	

	public IComplaintsService getComplaintsService() {
		return complaintsService;
	}

	public void setComplaintsService(IComplaintsService complaintsService) {
		this.complaintsService = complaintsService;
	}


	public String submitComplaints() {

		JSONObject jsonObject = null;
		Integer role = getRole();


		if (role == UTIL.roleuser) {
			Roomowner roomowner = (Roomowner) getHttpSession().getAttribute(UTIL.sessionUser);
			parameters.getComplaints().setRoomnumber(roomowner.getRoomnumber());
	//		jsonObject = complaintsService.submitComplaints(parameters.getComplaints(),parameters.getImages(),parameters.getImagesFileName(),parameters.getImagesContentType());
		} 

			jsonObject = complaintsService.submitComplaints(parameters.getComplaints(),parameters.getImages(),parameters.getImagesFileName(),parameters.getImagesContentType());

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public String listComplaints() {
		Integer role = getRole();
		JSONObject jsonObject = null;
	
			jsonObject = complaintsService.listComplaintsByPage(parameters.getPageNo(),parameters.getPageSize(),parameters.isFinished(),parameters.getRoomNumberSearchPattern());
	
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public String cancelComplaints() {

		javax.servlet.http.HttpSession httpSession = getHttpSession();
		Integer role = getRole();

		Complaints complaints = new Complaints();

		complaints.setId(parameters.getId());
		JSONObject jsonObject = null;

		if (role == UTIL.roleuser) {
			Roomowner roomowner = (Roomowner) httpSession.getAttribute(com.smartcommunity.util.UTIL.sessionUser);
			complaints.setRoomnumber(roomowner.getRoomnumber());
		//	jsonObject = complaintsService.cancelComplaints(complaints);
		} 

			jsonObject = complaintsService.cancelComplaints(complaints);

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	
	
	public String submitFeedback() {
		JSONObject jsonObject = null;
//		Integer role = getRole();
//		if (role == UTIL.roleuser) {
//			String roomnumber =  (String) getHttpSession().getAttribute(com.smartcommunity.util.UTIL.sessionRoomNumber);
//			parameters.getComplaints().setRoomnumber(roomnumber);
//		} 
		jsonObject = complaintsService.submitFeedback(parameters.getId(),parameters.getFeedback());


		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	
	
//	private boolean checkParameters(JSONObject jsonObject) {
//		if (jsonObject != null) {
//			JSONUtil.putSuccess(jsonObject, false);
//		}
//		if (complaints == null) {
//
//			if (jsonObject != null) {
//				JSONUtil.putCause(jsonObject, "参数不能为空");
//			}
//			return false;
//		}
//
//		/** 检查房间号是否正确 */
//		String checkparam = complaints.getRoomnumber();
//		if (checkparam == null) {
//
//			if (jsonObject != null) {
//				JSONUtil.putCause(jsonObject, "房间号不能为空");
//			}
//			return false;
//		}
//		if (! checkparam.matches(roomnumberPattern)) {
//			if (jsonObject != null) {
//				JSONUtil.putCause(jsonObject, "房间号格式不匹配");
//			}
//			return false;
//		}
//		// 检查姓名是否正确
//		checkparam = complaints.getContent();
//		if (checkparam == null ) {
//			if (jsonObject != null) {
//				JSONUtil.putCause(jsonObject, "投诉内容不能为空");
//			}
//			return false;
//		}
//
//		checkparam = complaints.getPhone();
//		if (checkparam == null ) {
//			if (jsonObject != null) {
//				JSONUtil.putCause(jsonObject, "身份号不能为空");
//				return false;
//			} 
//		}
//		
//		checkparam = roomowner.getPropertyowner();
//		if (checkparam == null ) {
//			if (jsonObject != null) {
//				JSONUtil.putCause(jsonObject, "产权人不能为空");
//				return false;
//			} 
//		}
//		checkparam = roomowner.getPropertyowneridentity();
//		if (checkparam == null ) {
//			if (jsonObject != null) {
//				JSONUtil.putCause(jsonObject, "产权人身份证号不能为空");
//				return false;
//			} 
//		}
//		
//		return true;
//	}
}
