package com.smartcommunity.action;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;

public class UserManageParams {

	Set<Integer> roles;
	private String name;
	private String password;
	private Roomowner roomowner;
	private Integer type;
	private Integer id;
	private Integer buildNo;
	private Integer unitNo;
	private Integer roomNo;
	
	private Integer pageNo;
	private Integer pageSize;
	

	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getRoomnumberPattern() {

		return UTIL.getRoomnumberSearchPattern(buildNo, unitNo, roomNo);
	}
	public String getConcreteRoomNumber() {

		return UTIL.getRoomnumber(buildNo, unitNo, roomNo);
	}

	public Integer getBuildNo() {
		return buildNo;
	}


	public void setBuildNo(Integer buildNo) {
		this.buildNo = buildNo;
	}


	public Integer getUnitNo() {
		return unitNo;
	}


	public void setUnitNo(Integer unitNo) {
		this.unitNo = unitNo;
	}


	public Integer getRoomNo() {
		return roomNo;
	}


	public void setRoomNo(Integer roomNo) {
		this.roomNo = roomNo;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public Set<Integer> getRoles() {
		return roles;
	}

	public void setRoles(Set<Integer> roles) {
		this.roles = roles;
	}


	public Roomowner getRoomowner() {
		return roomowner;
	}


	public void setRoomowner(Roomowner roomowner) {
		this.roomowner = roomowner;
	}


	public JSONObject checkUpdate() {
		JSONObject jsonObject = JSONUtil.getJsonObject(false);
		if (roomowner == null || roomowner.getId() == null) {
					JSONUtil.putCause(jsonObject, "没有指定要更新的记录");
				return jsonObject;
		}
	return null;
	}

	/**
	 * 检查参数的正确性
	 * 
	 * @param jsonObject
	 *            如果参数有错误，则返回错误信息
	 * @return
	 */
	public JSONObject checkRoomownerInsert() {
		JSONObject jsonObject = JSONUtil.getJsonObject(false);
		if (roomowner == null) {

			JSONUtil.putCause(jsonObject, "参数不能为空");
			return jsonObject;
		}

		/** 检查房间号是否正确 */
		String checkparam = roomowner.getRoomnumber();
		if (checkparam == null) {
			JSONUtil.putCause(jsonObject, "房间号不能为空");
			return jsonObject;
		}
		if (!UTIL.checkRoomnumber(checkparam)) {

			JSONUtil.putCause(jsonObject, "房间号格式不匹配,正确格式为 xxx-xxx-xxx");

			return jsonObject;
		}
		/* 检查姓名是否为空 */
		checkparam = roomowner.getName();
		if (checkparam == null || "".equals(checkparam)) {
			JSONUtil.putCause(jsonObject, "姓名不能为空");
			return jsonObject;
		}
		/** 检查身份证号是否正确 */
		checkparam = roomowner.getIdentity();
		if (checkparam == null || "".equals(checkparam)) {
			JSONUtil.putCause(jsonObject, "身份号不能为空");
			return jsonObject;
		}

		if (!UTIL.checkIdentity(checkparam)) {
			JSONUtil.putCause(jsonObject, "身份证号格式不正确");

			return jsonObject;
		}

		// 1为业主,2 为租户
		if (roomowner.getType() == null) {
			roomowner.setType(1);
		}
		if (roomowner.getType() == 1) {
			// 如果为业主，则检查这两项
			checkparam = roomowner.getPropertyowner();
			if (checkparam == null || "".equals(checkparam)) {
				JSONUtil.putCause(jsonObject, "产权人不能为空");
				return jsonObject;
			}

			checkparam = roomowner.getPropertyowneridentity();
			if (checkparam == null || "".equals(checkparam)) {

				JSONUtil.putCause(jsonObject, "产权人身份证号不能为空");

				return jsonObject;
			}
			if (! UTIL.checkIdentity(checkparam)) {

					JSONUtil.putCause(jsonObject, "身份证号格式不正确");
				return jsonObject;
			}
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
