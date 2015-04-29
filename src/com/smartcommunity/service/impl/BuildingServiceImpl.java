package com.smartcommunity.service.impl;

import java.util.HashSet;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.mapper.BuildingMapper;
import com.smartcommunity.mapper.RoomMapper;
import com.smartcommunity.pojo.Building;
import com.smartcommunity.pojo.BuildingExample;
import com.smartcommunity.pojo.Notice;
import com.smartcommunity.pojo.NoticeExample;
import com.smartcommunity.pojo.Room;
import com.smartcommunity.pojo.RoomExample;
import com.smartcommunity.service.IBuildingService;
import com.smartcommunity.util.DateUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;

import edu.hust.smartcommunity.paginator.domain.PageBounds;
import edu.hust.smartcommunity.paginator.domain.PageList;

public class BuildingServiceImpl implements IBuildingService {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(BuildingServiceImpl.class);
	private RoomMapper roomMapper;
	private BuildingMapper buildingMapper;

	public BuildingMapper getBuildingMapper() {
		return buildingMapper;
	}

	public void setBuildingMapper(BuildingMapper buildingMapper) {
		this.buildingMapper = buildingMapper;
	}

	public RoomMapper getRoomMapper() {
		return roomMapper;
	}

	public void setRoomMapper(RoomMapper roomMapper) {
		this.roomMapper = roomMapper;
	}

	@Override
	public JSONObject submitRoomInfo(Room room) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (room == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "房间信息为空");
			return jsonObject;
		}
		/** 设置楼栋信息 */
		if (room.getBuildingnumber() == null) {
			String[] temp = room.getNumber().split("-");
			room.setBuildingnumber(Integer.valueOf(temp[0]));
		}
		roomMapper.insertSelective(room);

		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject submitBuildingInfo(Building building) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (building == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "楼宇信息为空");
			return jsonObject;
		}
		buildingMapper.insertSelective(building);

		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject listBuildingInfoByPage(Integer pageNo, Integer pageSize,
			Integer buildingNumber) {
		// TODO Auto-generated method stub
		// 用于分页
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}

		PageBounds pageBounds = new PageBounds(pageNo, pageSize);
		// 用于查询条件
		BuildingExample buildingExample = new BuildingExample();
		if (buildingNumber != null) {
			buildingExample.or().andNumberEqualTo(buildingNumber);
		}
		buildingExample.setOrderByClause("number asc");
		// java.util.Map map = new java.util.HashMap<String, Object>();
		// map.put("page", page);
		// List<Notice> notices = noticeMapper.selectByExample(noticeExample);
		JSONObject jsonObject = null;
		PageList<Building> buildings = null;

		buildings = buildingMapper.selectByExample(buildingExample, pageBounds);

		System.out.println(buildings.get(0).getCompletiondate());
		JSONArray jsonArray = (JSONArray) com.alibaba.fastjson.JSON
				.toJSON(buildings);

		jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, buildings.getPaginator()
				.getTotalPages());
		return jsonObject;
	}

	@Override
	public JSONObject updateBuildingInfo(Building building) {
		// TODO Auto-generated method stub
		if (building == null || building.getId() == null) {
			JSONObject jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "更新楼栋信息不能为空");
			return jsonObject;
		}
		// if (building.getNumber() != null) {
		// BuildingExample buildingExample = new BuildingExample();
		// buildingExample.or().andNumberEqualTo(building.getNumber());
		// buildingMapper.updateByExampleSelective(building, buildingExample);
		// } else {
		buildingMapper.updateByPrimaryKeySelective(building);
		// }
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject deleteBuildingInfo(Integer buildingNumber) {
		// TODO Auto-generated method stub
		if (buildingNumber == null) {

			JSONObject jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "请输入要删除的楼栋号");
			return jsonObject;
		}
		BuildingExample buildingExample = new BuildingExample();
		buildingExample.or().andNumberEqualTo(buildingNumber);
		int result = buildingMapper.deleteByExample(buildingExample);
		System.out.println(result);
		if (result == 0) {
			JSONObject jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有要删除的楼栋信息");
			return jsonObject;
		}
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject updateRoomInfo(Room room) {
		if (room == null || room.getId() == null) {
			JSONObject jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "更新房间信息不能为空");
			return jsonObject;
		}
		/** 设置楼栋号 */
		if (room.getNumber() != null) {
			System.out.println("ayng");
			String[] temp = room.getNumber().split(
					com.smartcommunity.util.UTIL.ROOM_SPLIT);
			room.setBuildingnumber(Integer.valueOf(temp[0]));
			// com.smartcommunity.pojo.RoomExample roomExample = new
			// com.smartcommunity.pojo.RoomExample();
			// roomExample.or().andNumberEqualTo(room.getNumber());
			// roomMapper.updateByExampleSelective(room, roomExample);
		}
		int result = roomMapper.updateByPrimaryKeySelective(room);
		JSONObject jsonObject = null;
		if (result == 0) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有要更新的房间信息");
		} else {
			jsonObject = JSONUtil.getJsonObject(true);
		}
		return jsonObject;
	}

	@Override
	public JSONObject listRoomInfoByPage(Integer pageNo, Integer pageSize,
			String roomSearchPattern) {

		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		System.out.println(roomSearchPattern);

		PageBounds pageBounds = new PageBounds(pageNo, pageSize);
		// 用于查询条件
		RoomExample roomExample = new RoomExample();
		if (roomSearchPattern != null) {
			roomExample.or().andNumberLike(roomSearchPattern);
		}
		roomExample.setOrderByClause("buildingnumber asc");
		// java.util.Map map = new java.util.HashMap<String, Object>();
		// map.put("page", page);
		// List<Notice> notices = noticeMapper.selectByExample(noticeExample);
		JSONObject jsonObject = null;
		PageList<Room> rooms = null;

		rooms = roomMapper.selectByExample(roomExample, pageBounds);

		JSONArray jsonArray = (JSONArray) com.alibaba.fastjson.JSON
				.toJSON(rooms);

		jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, rooms.getPaginator().getTotalPages());
		return jsonObject;
	}

	@Override
	public JSONObject deleteRoomInfo(String roomNumber) {

		// TODO Auto-generated method stub
		if (roomNumber == null) {

			JSONObject jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "请输入要删除的房间号");
			return jsonObject;
		}
		RoomExample roomExample = new RoomExample();
		roomExample.or().andNumberEqualTo(roomNumber);
		int result = roomMapper.deleteByExample(roomExample);

		if (result == 0) {
			JSONObject jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有要删除的房间信息");
			return jsonObject;
		}
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject listBuildingNumber() {
		// TODO Auto-generated method stub
		List<Building> buildings = buildingMapper.selectNumberAndName();
		JSONArray jsonArray = (JSONArray) JSON.toJSON(buildings);
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);

		return jsonObject;
	}

	@Override
	public JSONObject listUnitNumber(Integer buildingNo) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (buildingNo == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "输入楼栋号");
			return jsonObject;
		}
		String pattern = com.smartcommunity.util.UTIL
				.getRoomnumberSearchPattern(buildingNo, null, null);

		com.smartcommunity.pojo.RoomExample roomExample = new com.smartcommunity.pojo.RoomExample();
		roomExample.or().andNumberLike(pattern);
		List<String> roomNumbers = roomMapper
				.selectRoomNumberByExample(roomExample);

		String[] splitStrings = null;
		java.util.Set<Integer> tempSet = new java.util.HashSet<Integer>();
		for (String string : roomNumbers) {
			splitStrings = string
					.split(com.smartcommunity.util.UTIL.ROOM_SPLIT);
			tempSet.add(Integer.valueOf(splitStrings[1]));
		}
		JSONObject jsonObject2 = null;
		JSONArray jsonArray = new JSONArray();
		for (Integer unit : tempSet) {

			jsonObject2 = new JSONObject();

		jsonObject2.put(UTIL.BUILDING_NAME, "" + unit + " 单元");
		jsonObject2.put(UTIL.BUILDING_NUMBER, unit );

		jsonArray.add(jsonObject2);
		tempSet.add(Integer.valueOf(splitStrings[1]));
	}
		
	//	JSONArray jsonArray = (JSONArray) JSON.toJSON(tempSet);

		jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		return jsonObject;
	}

	@Override
	public JSONObject listRoomNumber(Integer buildingNo, Integer unitNo) {

		JSONObject jsonObject = null;
		if (buildingNo == null || unitNo == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "输入楼栋号和单元号");
			return jsonObject;
		}
		String pattern = com.smartcommunity.util.UTIL
				.getRoomnumberSearchPattern(buildingNo, unitNo, null);

		com.smartcommunity.pojo.RoomExample roomExample = new com.smartcommunity.pojo.RoomExample();
		roomExample.or().andNumberLike(pattern);
		List<String> roomNumbers = roomMapper
				.selectRoomNumberByExample(roomExample);

		String[] splitStrings = null;
		java.util.Set<Integer> tempSet = new java.util.HashSet<Integer>();
//		for (String string : roomNumbers) {
//			splitStrings = string
//					.split(com.smartcommunity.util.UTIL.ROOM_SPLIT);
//			tempSet.add(Integer.valueOf(splitStrings[2]));
//		}

		JSONObject jsonObject2 = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		for (String string : roomNumbers) {

			jsonObject2 = new JSONObject();
		splitStrings = string
				.split(com.smartcommunity.util.UTIL.ROOM_SPLIT);
		jsonObject2.put(UTIL.BUILDING_NAME, "" + Integer.valueOf(splitStrings[2]) );
		jsonObject2.put(UTIL.BUILDING_NUMBER, Integer.valueOf(splitStrings[2]) );
		jsonArray.add(jsonObject2);
		tempSet.add(Integer.valueOf(splitStrings[1]));
	}
	//	JSONArray jsonArray = (JSONArray) JSON.toJSON(tempSet);

		jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		return jsonObject;
	}

	@Override
	public JSONObject listRoomInfoByCondition(Integer pageNo, Integer pageSize,
			Room room) {
		// TODO Auto-generated method stub
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		JSONObject jsonObject = null;
		if (room == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有要查询的条件");
			return jsonObject;
		}
		PageBounds pageBounds = new PageBounds(pageNo,pageSize);
		RoomExample roomExample = new RoomExample();
		RoomExample.Criteria criteria = roomExample.or();
		if (room.getState() != null) {
			criteria.andStateEqualTo(room.getState());
		}
		roomExample.setOrderByClause("buildingnumber asc");
		PageList<Room> rooms = roomMapper.selectByExample(roomExample, pageBounds);
		
		JSONArray jsonArray = (JSONArray) com.alibaba.fastjson.JSON
				.toJSON(rooms);

		jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, rooms.getPaginator().getTotalPages());
		
		return jsonObject;
	}

}
