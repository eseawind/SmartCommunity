package com.smartcommunity.service;

import java.io.File;
import java.util.List;




import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Complaints;
import com.smartcommunity.pojo.Repair;

public interface IComplaintsService {

	/**
	 * 提交投诉信息与图片
	 * @param complaints
	 * @return
	 */
	public JSONObject submitComplaints(Complaints complaints,
			java.util.List<File> images, List<String> imagesFileName,
			List<String> imagesContentType);

	public JSONObject listComplaintsByPage(Integer pageSize,Integer pageNo,boolean isfinished,String roomNumber);
	

	public JSONObject cancelComplaints(Complaints complaints);

	public JSONObject submitFeedback(Integer id, String feedback);
}
