package com.smartcommunity.service;

import java.io.File;
import java.util.List;




import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Repair;

public interface IRepairService {

	public JSONObject listAll();

	public JSONArray listAllUnprocessed();

	public boolean setProcessed(Repair repair);

	public JSONObject findUserRepairInfo();
	
	/**
	 * 撤消报修
	 * @param repair
	 * @return
	 */
	public JSONObject cancelRepair(Repair repair);

	/**
	 * 列出已完成 或 未完成的报修信息,房间为空时查询所有
	 * 
	 * @param pageSize
	 *            每页大小
	 * @param pageNo
	 *            页号
	 * @param isFinished
	 *            是否已经完成
	 * @return
	 */
	JSONObject listRepariInfoByPage(Integer pageNo, Integer pageSize,
			boolean isFinished,String roomnumber);

	/**
	 * 提交用户反馈
	 * 
	 * @param repair
	 *            包含报修 id 与 反馈信息
	 * @return 返回反馈信息
	 */
	public JSONObject submitFeedback(Repair repair);

	/**
	 * 提交报修文字信息与保存图片
	 * 
	 * @param repair
	 * @return
	 */
	public JSONObject submitRepairInfo(Repair repair,
			java.util.List<File> images, List<String> imagesFileName,
			List<String> imagesContentType);

	public JSONObject setState(Integer repairid,Integer state);
}
