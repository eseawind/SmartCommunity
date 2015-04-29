package com.smartcommunity.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Repair;
import com.smartcommunity.service.IDeviceService;
import com.smartcommunity.service.IRepairService;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;

public class RepairParams {


	private Integer pageSize;	// 每页大小
	private Integer pageNo;	// 页号
	
	private Repair repair;
	// 用于指定查询是否已完成的报修信息
	private boolean Finished;

	// 用于上传图片
    private List<File> images ;  
    private List<String> imagesFileName ;  
    private List<String> imagesContentType ;
    

	private Integer id;
	private Integer userid;
	private Integer state;
	private Integer processedstate;

	/** 指定房间号 */
	private Integer buildNo;
	private Integer unitNo;
	private Integer roomNo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
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
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Repair getRepair() {
		if (repair != null) {
			repair.setDate(new Date());
		}
		return repair;
	}
	public Repair getRepairWithParams() {
		if (repair != null) {
			repair.setDate(new Date());
			repair.setRoomnumber(UTIL.getRoomnumber(buildNo, unitNo, roomNo));
		}
		return repair;
	}
	
	public void setRepair(Repair repair) {
		this.repair = repair;
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
      public JSONObject checkSubmit() {
    		JSONObject  jsonObject = JSONUtil.getJsonObject(false);

  		if (repair == null) {
			JSONUtil.putCause(jsonObject, "没有参数");
			return jsonObject;
		}
  		return null;
      }
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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

	public String getRoomNumberSearchPattern() {
		return com.smartcommunity.util.UTIL.getRoomnumberSearchPattern(buildNo, unitNo, roomNo);

	}
	public Integer getProcessedstate() {
		return processedstate;
	}
	public void setProcessedstate(Integer processedstate) {
		this.processedstate = processedstate;
	}
}
