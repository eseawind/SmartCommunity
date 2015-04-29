package com.smartcommunity.action;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Decorate;
import com.smartcommunity.pojo.Decoratefee;
import com.smartcommunity.util.JSONUtil;

public class DecorateParams {

	private Decorate decorate;
	private Decoratefee decoratefee;
	private String roomnumber;
	private Integer pageNo;
	private Integer pageSize;

	public String getRoomnumber() {
		return roomnumber;
	}
	public void setRoomnumber(String roomnumber) {
		this.roomnumber = roomnumber;
	}

	private final String roomnumberPattern = "[0-9]-[0-9]-[0-9]{3}";
	public Decorate getDecorate() {
		return decorate;
	}
	public void setDecorate(Decorate decorate) {
		this.decorate = decorate;
	}
	public Decoratefee getDecoratefee() {
		return decoratefee;
	}
	public void setDecoratefee(Decoratefee decoratefee) {
		this.decoratefee = decoratefee;
	}
	
	public boolean checkDecorate(JSONObject jsonObject) {

		if (jsonObject != null) {
			JSONUtil.putSuccess(jsonObject, false);
		}
		if (decorate == null ) {
			if (jsonObject != null) {

				JSONUtil.putCause(jsonObject, "参数不能为空");
			}
			return false;
		}
		// 检查房间号是否正确
		String roomnumber = decorate.getRoomnumber();
		if (roomnumber == null || !roomnumber.matches(roomnumberPattern)) {
			if (jsonObject != null) {
				JSONUtil.putCause(jsonObject, "房间号不正确");
			}
			return false;
		}
		return true;
	}

	public boolean checkDecoratefee(JSONObject jsonObject) {
		/** 检查房间号是否正确 */

		if (jsonObject != null) {
			JSONUtil.putSuccess(jsonObject, false);
		}
		if (decoratefee == null ) {
			if (jsonObject != null) {

				JSONUtil.putCause(jsonObject, "参数不能为空");
			}
			return false;
		}
		// 检查房间号是否正确
		String roomnumber = decoratefee.getRoomnumber();
		if (roomnumber == null || !roomnumber.matches(roomnumberPattern)) {
			if (jsonObject != null) {
				JSONUtil.putCause(jsonObject, "房间号不正确");
			}
			return false;
		}
		return true;
	}
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
}
