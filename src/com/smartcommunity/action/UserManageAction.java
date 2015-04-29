package com.smartcommunity.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.pojo.Userrolejoin;
import com.smartcommunity.service.IUserManageService;
import com.smartcommunity.util.ExceptionUtil;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;

public class UserManageAction extends BaseActionSupport<UserManageParams> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(UserManageAction.class);
	private IUserManageService userManageService;

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public IUserManageService getUserManageService() {
		return userManageService;
	}

	public void setUserManageService(IUserManageService userManageService) {
		this.userManageService = userManageService;
	}

	/**
	 * 提交业主信息,设置密码为身份证后六位
	 * 
	 * @return
	 */
	public String submitRoomownerInfo() {

		JSONObject jsonObject = null;
		jsonObject = parameters.checkRoomownerInsert();
		if (jsonObject != null) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}

		try {
			jsonObject = userManageService.submitRoomownerInfo(
					parameters.getRoomowner(), parameters.getRoles());
		} catch (org.springframework.dao.DuplicateKeyException e) {
			// TODO Auto-generated catch block
			logger.error(ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "房间号[" + parameters.getRoomowner().getRoomnumber() + "]已经存在");
		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	public String login() throws UnsupportedEncodingException {
		JSONObject jsonObject = null;
		try {
			jsonObject = userManageService.login(parameters.getName(),
					parameters.getPassword());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(ExceptionUtil.getStackTrack(e));
			JSONUtil.putCause(jsonObject, e);
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		
		if (jsonObject == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "用户名或密码错误");
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}

		/** 成功登陆 */
		Roomowner roomowner = (Roomowner) jsonObject.get("roomowner");

		// 获取 http 请求
		HttpServletRequest httpServletRequest = ServletActionContext
				.getRequest();
		// 如果没有就创建
		HttpSession httpSession = httpServletRequest.getSession(true);
		httpSession.setAttribute(UTIL.sessionUser, roomowner); // 保存用户到会话
		if (roomowner != null) {
			httpSession.setAttribute(UTIL.sessionRoomNumber,roomowner.getRoomnumber()); // 保存用户到会话
		}
		httpSession.setMaxInactiveInterval(360*24*60*60); // 会话超时,单位为 秒

		javax.servlet.http.Cookie cookieusername = null;

			cookieusername = new javax.servlet.http.Cookie(UTIL.COOKIE_NAME,
					java.net.URLEncoder.encode(parameters.getName(), "utf-8"));
		cookieusername.setMaxAge(Integer.MAX_VALUE);
		javax.servlet.http.HttpServletResponse response = ServletActionContext
				.getResponse();
		response.addCookie(cookieusername);

		javax.servlet.http.Cookie cookiepassword = new javax.servlet.http.Cookie(
				UTIL.COOKIE_PASSWORD, parameters.getPassword());
		cookiepassword.setMaxAge(Integer.MAX_VALUE);
		response.addCookie(cookiepassword);

		java.util.Set<Integer> roleSet = (Set<Integer>) jsonObject
				.get(UTIL.sessionRoles);
		java.util.Map<String,Set<Integer>> map = new java.util.HashMap<>();
		
		jsonObject.clear();
		jsonObject.put("success", true);
		if (roomowner != null) {

			jsonObject.put("telephone", roomowner.getTelephone());
			jsonObject.put("room", roomowner.getRoomnumber());
			jsonObject.put("name", roomowner.getName());
			jsonObject.put("id", roomowner.getId());
		}

		JSONArray jsonArray = null;
//		if (roleSet != null) {
//			JSONObject tempJsonObject = null;
//			jsonArray = new JSONArray();
//			for  (Integer i : roleSet) {
//				tempJsonObject = new JSONObject();
//				tempJsonObject.put("role", i);
//				jsonArray.add(tempJsonObject);
//			}
			
			jsonObject.put(UTIL.sessionRoles, roleSet);
//		}

		System.out.println(jsonObject);
		/** 保存用户角色到会话 */
		httpSession.setAttribute(UTIL.sessionRoles, roleSet);
		if (roomowner != null) {
			
		httpSession.setAttribute(UTIL.sessionTelephone, roomowner.getTelephone());

		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		if (roomowner != null) {

			logger.info("用户 {} , 房间 {} 登入 ", roomowner.getName(),
					roomowner.getRoomnumber());
		}
		return SUCCESS;
	}

	public String updateRoomownerInfo() {

		JSONObject jsonObject = parameters.checkUpdate();
		if (jsonObject != null) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		Integer role = getRole();
		if (role == UTIL.roleuser) {
			// 用户不能更新自己的房间号与用户密码对应关系
			Roomowner roomowner = (Roomowner) getHttpSession().getAttribute(UTIL.sessionUser);
			parameters.getRoomowner().setRoomnumber(null);
			parameters.getRoomowner().setId(roomowner.getId());
		} 
			parameters.getRoomowner().setUserpassid(null);
			try {

				jsonObject = userManageService.updateRoomownerInfo(parameters
						.getRoomowner());
			} catch (DuplicateKeyException e) {

				jsonObject = JSONUtil.getJsonObject(false);
				JSONUtil.putCause(jsonObject, "房间编号已存在");
			} catch (BadSqlGrammarException e) {
				jsonObject = JSONUtil.getJsonObject(false);
				JSONUtil.putCause(jsonObject, "没有要更新的信息");
			} catch (DataIntegrityViolationException e) {
				jsonObject = JSONUtil.getJsonObject(false);
				JSONUtil.putCause(jsonObject, "房间编号可能不存在");
			}

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	public String listRoomownerInfo() {

		JSONObject jsonObject = null;
	//	Integer role = getRole();

//		try {
	//	if (role == UTIL.roleuser) {
//			Roomowner roomowner = (Roomowner) getHttpSession().getAttribute(UTIL.sessionUser);
//			if (roomowner.getRoomnumber() != null) {
//				jsonObject = userManageService.listRoomownerInfo(
//						null,roomowner.getRoomnumber());
//			}
//		} else if (role == UTIL.rolemanager) {

			jsonObject = userManageService.listRoomownerInfo(parameters.getPageNo(),parameters.getPageSize(),
					parameters.getType(), parameters.getRoomnumberPattern());
//		}
//		} catch (NullPointerException e) {

//			logger.error(ExceptionUtil.getStackTrack(e));
//			jsonObject = JSONUtil.getJsonObject(false);
//			JSONUtil.putCause(jsonObject,"房间号不存在");
//		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);

		return SUCCESS;
	}

	public String logout() throws Exception {
		javax.servlet.http.HttpSession session = ServletActionContext
				.getRequest().getSession();
		Roomowner roomowner = (Roomowner) session.getAttribute(UTIL.sessionUser);
		session.invalidate();

		javax.servlet.http.Cookie cookieusername = new javax.servlet.http.Cookie(
				UTIL.COOKIE_NAME, java.net.URLEncoder.encode("", "utf-8"));
		cookieusername.setMaxAge(0);
		javax.servlet.http.HttpServletResponse response = ServletActionContext
				.getResponse();
		response.addCookie(cookieusername);

		javax.servlet.http.Cookie cookiepassword = new javax.servlet.http.Cookie(
				UTIL.COOKIE_PASSWORD, "");
		cookiepassword.setMaxAge(0);
		response.addCookie(cookiepassword);
		//
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		if (roomowner != null) {

			logger.info("用户 {} , 房间 {} 登出 ", roomowner.getName(),
					roomowner.getRoomnumber());
		}
		return SUCCESS;
	}

	public String deleteRoomowner()  {
		JSONObject jsonObject = null;

		jsonObject = userManageService.deleteRoomowner(parameters.getConcreteRoomNumber());
System.out.println(parameters.getRoomnumberPattern());
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		logger.info("删除用户 id = {} 成功", parameters.getId());
		return SUCCESS;
	}
	
	public String addManager()  {
		JSONObject jsonObject = null;
	//	try {
			jsonObject = userManageService.addManager(parameters.getName(), parameters.getPassword());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error(ExceptionUtil.getStackTrack(e));
//			jsonObject = JSONUtil.getJsonObject(false);
//			JSONUtil.putCause(jsonObject, e);
//			return SUCCESS;
//		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public String getTelephone()  {
		JSONObject jsonObject = null;
		HttpSession httpSession = ServletActionContext.getRequest().getSession();
		String telephone = null;
		if (httpSession != null) {
			telephone = (String) httpSession.getAttribute(UTIL.sessionTelephone);
		}
		if (telephone == null) {
			telephone = "";
		}
		jsonObject = JSONUtil.getJsonObject(true);
		jsonObject.put(UTIL.sessionTelephone, telephone);
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	
	public String getUserDetails()  {
		JSONObject jsonObject = null;
		HttpSession httpSession = ServletActionContext.getRequest().getSession(false);
		Roomowner roomowner = null;
		if (httpSession != null) {
			roomowner =  (Roomowner) httpSession.getAttribute(UTIL.sessionUser);
		}
		if (roomowner == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "会话中没有用户信息");
		} else {

			jsonObject = (JSONObject) com.alibaba.fastjson.JSON.toJSON(roomowner);
			JSONUtil.putSuccess(jsonObject, true);
		}
		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
}
