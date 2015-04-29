package com.smartcommunity.action;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Building;
import com.smartcommunity.pojo.Room;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;

public class BuildingParams {

	private Room room;
	private Building building;
	private Integer pageNo;
	private Integer pageSize;
	private Integer buildNo;
	private Integer unitNo;
	private Integer roomNo;

	public String getRoomnumberSearchPattern() {
		return UTIL.getRoomnumberSearchPattern(buildNo, unitNo, roomNo);
	}

	public String getRoomNumber() {
		if (room == null)
			return null;
		return room.getNumber();
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
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

	public JSONObject checkRoomInsert() {
		JSONObject jsonObject = JSONUtil.getJsonObject(false);

		if (room == null) {
			JSONUtil.putCause(jsonObject, "房间信息不能为空");
			return jsonObject;
		}
		if (room.getNumber() == null) {

			JSONUtil.putCause(jsonObject, "房间编号不能为空");
			return jsonObject;
		}
		if (!com.smartcommunity.util.UTIL.checkRoomnumber(room.getNumber())) {

			JSONUtil.putCause(jsonObject, "房间号格式不正确");
			return jsonObject;
		}

		return null;
	}

	public JSONObject checkBuildingInsert() {
		JSONObject jsonObject = JSONUtil.getJsonObject(false);

		if (building == null) {
			JSONUtil.putCause(jsonObject, "楼宇信息不能为空");
			return jsonObject;
		}
		if (building.getNumber() == null) {

			JSONUtil.putCause(jsonObject, "楼宇编号不能为空");
			return jsonObject;
		}

		return null;
	}

	public JSONObject checkBuildingUpdate() {

		JSONObject jsonObject = JSONUtil.getJsonObject(false);

		if (building == null) {
			JSONUtil.putCause(jsonObject, "楼宇信息不能为空");
			return jsonObject;
		}
		if (building.getId() == null) {

			JSONUtil.putCause(jsonObject, "没有指定楼宇编号");
			return jsonObject;
		}
		return null;
	}

	public JSONObject checkRoomUpdate() {

		JSONObject jsonObject = JSONUtil.getJsonObject(false);

		if (room == null) {
			JSONUtil.putCause(jsonObject, "楼宇信息不能为空");
			return jsonObject;
		}
		if (room.getId() == null) {

			JSONUtil.putCause(jsonObject, "没有指定楼宇id");
			return jsonObject;
		}
		if (room.getNumber() != null && !UTIL.checkRoomnumber(room.getNumber())) {

			JSONUtil.putCause(jsonObject, "房间号格式不正确");
			return jsonObject;
		}
		return null;
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

}
