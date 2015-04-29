package com.smartcommunity.action;

import java.io.File;
import java.util.List;

import com.smartcommunity.pojo.Complaints;
import com.smartcommunity.util.UTIL;

public class ComplaintsParams {


	private Complaints complaints;
	private Integer pageSize;	// 每页大小
	private Integer pageNo;	// 页号
	
	private Integer buildNo;
	private Integer unitNo;
	private Integer roomNo;
	// 用于指定查询是否已完成的报修信息
	private boolean Finished;
	private String feedback;

	private Integer id;
	// 用于上传图片
    private List<File> images ;  
    private List<String> imagesFileName ;  
    private List<String> imagesContentType ;
	public Complaints getComplaints() {
		if (complaints != null && complaints.getRoomnumber() == null)
			complaints.setRoomnumber(UTIL.getRoomnumber(buildNo, unitNo, roomNo));
		return complaints;
	}
	public void setComplaints(Complaints complaints) {
		this.complaints = complaints;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public String getRoomNumberSearchPattern() {
		return UTIL.getRoomnumberSearchPattern(buildNo, unitNo, roomNo);
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public boolean isFinished() {
		return Finished;
	}
	public void setFinished(boolean finished) {
		Finished = finished;
	}
	public List<File> getImages() {
		return images;
	}
	public void setImages(List<File> images) {
		this.images = images;
	}
	public List<String> getImagesFileName() {
		return imagesFileName;
	}
	public void setImagesFileName(List<String> imagesFileName) {
		this.imagesFileName = imagesFileName;
	}
	public List<String> getImagesContentType() {
		return imagesContentType;
	}
	public void setImagesContentType(List<String> imagesContentType) {
		this.imagesContentType = imagesContentType;
	}  
	
	public boolean checkInsert(com.alibaba.fastjson.JSONObject jsonObject) {
		if (jsonObject == null) {
			jsonObject = com.smartcommunity.util.JSONUtil.getJsonObject(false);
		}
		if (complaints.getRoomnumber() != null && 
				!complaints.getRoomnumber().matches(UTIL.roomnumberPattern)) {

			com.smartcommunity.util.JSONUtil.putCause(jsonObject, "房间号格式不正确");
		return false;
		}
	
	return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
      
}
