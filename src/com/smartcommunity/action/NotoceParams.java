package com.smartcommunity.action;


import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Notice;
import com.smartcommunity.util.JSONUtil;

public class NotoceParams {

	private Notice notice ;
	private Integer buildNo;
	private Integer unitNo;
	private Integer roomNo;
	private Boolean isread;
	private Integer id;
	private Integer pageSize; // 页大小
	private Integer pageNo; // 页号
	private String keywords;	// 关键字
	private Boolean markReaded; 	// 是否将公告设为已读
	public Notice getNotice() {
		if (notice == null) {
			return notice;
		}
		notice.setDate(new Date());

		notice.setRoom(com.smartcommunity.util.UTIL.getNoticeRoomNumber(buildNo, unitNo, roomNo));
		return notice;
	}
	public void setNotice(Notice notice) {
		this.notice = notice;
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
	public JSONObject checkInsert( ) {
		JSONObject jsonObject = JSONUtil.getJsonObject(false);
	
		if (notice == null) {
			JSONUtil.putCause(jsonObject, "输入信息不能为空");
			return jsonObject;
		}
		if (notice.getTitle() == null) {

			JSONUtil.putCause(jsonObject, "标题不能为空");
			return jsonObject;
		}
		if (notice.getContent() == null) {

			JSONUtil.putCause(jsonObject, "内容不能为空");
			return jsonObject;
		}
		if (notice.getAuthor() == null) {

			JSONUtil.putCause(jsonObject, "发布者不能为空");
			return jsonObject;
		}
		
		return null;
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getIsread() {
		return isread;
	}
	public void setIsread(Boolean isread) {
		this.isread = isread;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Boolean getMarkReaded() {
		return markReaded;
	}
	public void setMarkReaded(Boolean markReaded) {
		this.markReaded = markReaded;
	}


}
