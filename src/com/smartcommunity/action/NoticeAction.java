package com.smartcommunity.action;

import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.smartcommunity.pojo.Notice;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.service.INoticeService;
import com.smartcommunity.util.DateUtil;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;
import com.smartcommunity.websocket.WebSocketMessageInboundPool;









import org.apache.struts2.ServletActionContext;
import org.slf4j.*;
/**
 * 管理通知 action
 * @author Administrator
 *
 */
public class NoticeAction extends BaseActionSupport<NotoceParams>{

	Logger logger = LoggerFactory.getLogger(NoticeAction.class);
	private INoticeService noticeService;
	private InputStream inputStream;

	/**
	 * 发布公告
	 * @return
	 */
	public String publishNotice() {

	//	model.setDate(new Date());
		JSONObject jsonObject = parameters.checkInsert();
		if (jsonObject != null ) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		Notice notice = parameters.getNotice();
		try {
			jsonObject = noticeService.publishNotice(notice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(com.smartcommunity.util.ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
			
		}
		
//		// 如果发布成功则通过 websocket 发送给所有已连接的用户
//		if (JSONUtil.getJsonStatus(jsonObject) == true) {
//			JSONObject noticeJsonObject = (JSONObject) com.alibaba.fastjson.JSON.toJSON(notice);
//			noticeJsonObject.put("date", DateUtil.dateToString(notice.getDate()));
//			WebSocketMessageInboundPool.sendMessage(noticeJsonObject.toString());
//		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	/**
	 * 分页形式查询公告信息
	 * @return
	 */
	public String listNoticeByPage() {
		JSONObject jsonObject = null;
		Integer role = getRole();
		/** 为普通用户 */
		if (role == UTIL.roleuser) {

			javax.servlet.http.HttpSession httpSession = getHttpSession();
			String roomnumber = (String) httpSession.getAttribute(com.smartcommunity.util.UTIL.sessionRoomNumber);
		
			jsonObject = noticeService.listUserNotice(parameters.getPageNo(), parameters.getPageSize(),roomnumber,parameters.getIsread(),parameters.getMarkReaded());
		} else {

				jsonObject = noticeService.listNoticeByPage(parameters.getPageNo(), parameters.getPageSize());
		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	
	public String listNoticeByKeyWords() {
		JSONObject jsonObject = null;
		Integer role = getRole();


				jsonObject = noticeService.listNoticeByKeyWords(parameters.getPageNo(), parameters.getPageSize(),parameters.getKeywords());
				
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	public String setIsReaded() {
		JSONObject jsonObject = null;
		Integer role = getRole();
		if (role == UTIL.roleuser) {
			javax.servlet.http.HttpSession httpSession = getHttpSession();
			com.smartcommunity.pojo.Roomowner roomowner = (Roomowner) httpSession.getAttribute(com.smartcommunity.util.UTIL.sessionUser);
			jsonObject = noticeService.setIsReaded(parameters.getId(), roomowner.getRoomnumber(), parameters.getIsread());
		} else if (role == UTIL.rolemanager){
			jsonObject = noticeService.setIsReaded(parameters.getId(),null, parameters.getIsread());
		}

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public INoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
