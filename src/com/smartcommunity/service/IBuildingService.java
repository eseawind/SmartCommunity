package com.smartcommunity.service;


import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Building;
import com.smartcommunity.pojo.Room;

public interface IBuildingService {


	public JSONObject submitBuildingInfo(Building building);

	public JSONObject updateBuildingInfo(Building building);

	public JSONObject listBuildingInfoByPage(Integer pageNo,Integer pageSize,Integer buildingNumber);
	

	public JSONObject deleteBuildingInfo(Integer buildingNumber);
	/** 房间信息 */
	public JSONObject submitRoomInfo(Room room);

	public JSONObject updateRoomInfo(Room room);
	/**
	 * 通过房间、楼栋、单元号查询
	 * @param pageNo
	 * @param pageSize
	 * @param roomSearchPattern
	 * @return
	 */
	public JSONObject listRoomInfoByPage(Integer pageNo,Integer pageSize,String roomSearchPattern);
	/**
	 * 通过房间条件查询
	 * @param pageNo
	 * @param pageSize
	 * @param room
	 * @return
	 */
	public JSONObject listRoomInfoByCondition(Integer pageNo,Integer pageSize,Room room);
	

	public JSONObject deleteRoomInfo(String roomNumber);
	
	
	/**
	 * 查询楼宇号
	 * @return
	 */
	public JSONObject listBuildingNumber();
	/**
	 * 查询单元号
	 * @param buildingNo
	 * @return
	 */
	public JSONObject listUnitNumber(Integer buildingNo);

	public JSONObject listRoomNumber(Integer buildingNo,Integer unitNo);
}
