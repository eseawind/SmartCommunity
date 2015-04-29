package com.smartcommunity.service;



import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Notice;

public interface INoticeService {

	/**
	 * 发布公告 接口
	 * @param notice : 公告内容
	 * @return
	 */
	public JSONObject publishNotice(Notice notice);
	
	/**
	 * 查询公告历史记录,物业使用
	 * @param pageNo : 要查询的页数
	 * @param pageSize : 每页显示的记录个数
	 * @return
	 */
	public JSONObject listNoticeByPage(Integer pageNo,Integer pageSize);

	public JSONObject listNoticeByKeyWords(Integer pageNo,Integer pageSize,String keywords);
	/**
	 * 用户查询发给自己的公告
	 * @param pageNo
	 * @param pageSize
	 * @param roomNumber
	 * @param isread	是否查询已读公告
	 * @param markReaded	是否易将查出来的公告设为已读
	 * @return
	 */
	public JSONObject listUserNotice(Integer pageNo,Integer pageSize,String roomNumber,Boolean isread, Boolean markReaded);

	public JSONObject setIsReaded(Integer id,String roomNumber,Boolean isread);

}
