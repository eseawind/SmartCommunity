package com.smartcommunity.service.impl;

import java.util.HashMap;
import java.util.List;

















import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.interceptor.Page;
import com.smartcommunity.mapper.NoticeMapper;
import com.smartcommunity.mapper.RoomownerMapper;
import com.smartcommunity.mapper.UserNoticeMapper;
import com.smartcommunity.pojo.Notice;
import com.smartcommunity.pojo.NoticeExample;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.pojo.RoomownerExample;
import com.smartcommunity.pojo.UserNotice;
import com.smartcommunity.service.INoticeService;
import com.smartcommunity.util.DateUtil;
import com.smartcommunity.util.JSONUtil;

import edu.hust.smartcommunity.paginator.domain.PageBounds;
import edu.hust.smartcommunity.paginator.domain.PageList;

public class NoticeServiceImpl implements INoticeService {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(NoticeServiceImpl.class);
	NoticeMapper noticeMapper;
	UserNoticeMapper userNoticeMapper;
	RoomownerMapper roomownerMapper;
	public RoomownerMapper getRoomownerMapper() {
		return roomownerMapper;
	}
	public void setRoomownerMapper(RoomownerMapper roomownerMapper) {
		this.roomownerMapper = roomownerMapper;
	}
	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject publishNotice(Notice notice) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		/** 插入公告 */
		noticeMapper.insertSelective(notice);

		String room = notice.getRoom();
		if (room == null) {
			throw new NullPointerException("公告对象为空");
		}
		/** 判断公告的发布对象的房间搜索条件 */
		StringBuilder stringBuilder = new StringBuilder();
		String[] strings = room.split("-");
		if (Integer.valueOf(strings[0]) != 0) {
			stringBuilder.append(strings[0]);
			if (Integer.valueOf(strings[1]) != 0) {
				stringBuilder.append("-" + strings[1]);
				if (Integer.valueOf(strings[2]) != 0) {
					stringBuilder.append("-" + strings[2]);
				}
			}
		}

		RoomownerExample roomownerExample = new RoomownerExample();
		if (stringBuilder.length() > 0) {
			stringBuilder.append("%");
			roomownerExample.or().andRoomnumberLike(stringBuilder.toString());
		}
		/** 查询所有符合条件的用户 */
		List<com.smartcommunity.pojo.Roomowner> roomowners = roomownerMapper.selectRoomnumberByExample(roomownerExample);
		List<UserNotice> userNotices = new java.util.ArrayList<>();
		UserNotice userNotice = null;
		for (Roomowner roomowner : roomowners) {
			userNotice = new UserNotice();
			userNotice.setNoticeid(notice.getId());
			userNotice.setRoomnumber(roomowner.getRoomnumber());
			userNotice.setIsread(false);
			userNotices.add(userNotice);
		}
		/** 公告插入用户公告对应的表中 */
		if (userNotices.size() > 0 )
			userNoticeMapper.insertMulti(userNotices);

		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}
	public NoticeMapper getNoticeMapper() {
		return noticeMapper;
	}
	public void setNoticeMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}
	public UserNoticeMapper getUserNoticeMapper() {
		return userNoticeMapper;
	}
	public void setUserNoticeMapper(UserNoticeMapper userNoticeMapper) {
		this.userNoticeMapper = userNoticeMapper;
	}
	/**
	 * 查询所有的公告
	 */
	@Override
	public JSONObject listNoticeByPage(Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
		// 用于分页
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		PageBounds pageBounds = new PageBounds(pageNo,pageSize);
		// 用于查询条件
		NoticeExample noticeExample = new NoticeExample() ;
		noticeExample.setOrderByClause("date desc");
//		java.util.Map map = new java.util.HashMap<String, Object>();
//		map.put("page", page);
//		List<Notice> notices = noticeMapper.selectByExample(noticeExample);
		JSONObject jsonObject = null;
		PageList<Notice> notices;
		/** 查询公告 */
			notices = noticeMapper.selectByExample(noticeExample,pageBounds);

			/** 将房间格式转变为字符串 */
			for (Notice notice : notices) {
				notice.setRoom(com.smartcommunity.util.UTIL.splitRoomNumber(notice.getRoom()));
			}
		JSONArray jsonArray = (JSONArray) JSON.toJSON(notices);
		jsonObject = JSONUtil.getJsonObject(true);

		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, notices.getPaginator().getTotalPages());
		return jsonObject;
	}
	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject listUserNotice(Integer pageNo, Integer pageSize,
			String roomNumber,Boolean isread, Boolean markReaded) {
		// TODO Auto-generated method stub
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		} 
		JSONObject jsonObject = null;
		if (roomNumber == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "房间号为空");
			return jsonObject;
		}

		PageBounds pageBounds = new PageBounds(pageNo,pageSize);
		com.smartcommunity.pojo.UserNoticeExample userNoticeExample = new com.smartcommunity.pojo.UserNoticeExample();
		com.smartcommunity.pojo.UserNoticeExample.Criteria criteria = userNoticeExample.or();
		criteria.andRoomnumberEqualTo(roomNumber);
		if (isread != null) {
			criteria.andIsreadEqualTo(isread);
		}
		/** 查询用户公告连接表 */
		PageList<UserNotice> userNotices = userNoticeMapper.selectByExample(userNoticeExample,pageBounds);
		java.util.List<Integer> noticeidlList = new java.util.ArrayList<>();

 		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		for (UserNotice userNotice : userNotices) {
			if (markReaded != null && markReaded == true) {
			userNotice.setIsread(true);
			userNoticeMapper.updateByPrimaryKeySelective(userNotice);
			}
			map.put(userNotice.getNoticeid(), userNotice.getId());
			noticeidlList.add(userNotice.getNoticeid());
		}
		/** 转换 报修 id 为 用户报修 id */
 		
		NoticeExample noticeExample = new NoticeExample ();
		// 如果没有数据就返回空
		if (noticeidlList.size() <= 0) {

			jsonObject = JSONUtil.getJsonObject(true);

			JSONUtil.putResult(jsonObject, new JSONArray() );
			return jsonObject;
		}
		noticeExample.or().andIdIn(noticeidlList);
		noticeExample.setOrderByClause("date desc");
		List<Notice> notices = noticeMapper.selectByExample(noticeExample);
		
		/** 将房间格式转变为字符串 */
		for (Notice notice : notices) {
			notice.setId(map.get(notice.getId()));
			notice.setRoom(com.smartcommunity.util.UTIL.splitRoomNumber(notice.getRoom()));
		}

		JSONArray jsonArray = (JSONArray) JSON.toJSON(notices);
		jsonObject = JSONUtil.getJsonObject(true);

		JSONUtil.putResult(jsonObject, jsonArray);
		jsonObject.put("totalpage",userNotices.getPaginator().getTotalPages());
		return jsonObject;
	}
	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject setIsReaded(Integer id, String roomNumber, Boolean isread) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (id == null) {

			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "参数不正确");
			return jsonObject;
		}
		UserNotice userNotice = userNoticeMapper.selectByPrimaryKey(id);
		if (userNotice == null) {

			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有要设置的公告");
			return jsonObject;
		}
		if (roomNumber != null) {
			if (!roomNumber.equals(userNotice.getRoomnumber())) {
				jsonObject = JSONUtil.getJsonObject(false);
				JSONUtil.putCause(jsonObject, "房间号不一致");
				return jsonObject;
			}
		}
		if (isread == null) {
			isread = true;
		}
		System.out.println("kldfj"+isread);
		userNotice.setIsread(isread);
		userNoticeMapper.updateByPrimaryKeySelective(userNotice);
		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}
	@Override
	public JSONObject listNoticeByKeyWords(Integer pageNo, Integer pageSize,
			String keywords) {

		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		PageBounds pageBounds = new PageBounds(pageNo,pageSize);
		NoticeExample noticeExample = new NoticeExample();

		if (keywords != null && !"".equals(keywords)) {
			String [] keyStrings = keywords.split(" ");
			for (String key : keyStrings) {
				String value = "%" +key + "%";
				System.out.println(value);
				noticeExample.or().andTitleLike(value);
				noticeExample.or().andContentLike(value);
			}
		}
		noticeExample.setOrderByClause("date desc");
		PageList<Notice> notices = noticeMapper.selectByExample(noticeExample,pageBounds);
		/** 将房间格式转变为字符串 */
		for (Notice notice : notices) {
			notice.setRoom(com.smartcommunity.util.UTIL.splitRoomNumber(notice.getRoom()));
		}
		JSONArray jsonArray = (JSONArray) JSON.toJSON(notices);
		JSONObject jsonObject = JSONUtil.getJsonObject(true);

		JSONUtil.putResult(jsonObject, jsonArray);
		jsonObject.put("totalpage",notices.getPaginator().getTotalPages());
		return jsonObject;
	}





}
