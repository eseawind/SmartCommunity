package com.smartcommunity.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;








import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.mapper.ComplaintsMapper;
import com.smartcommunity.mapper.ComplaintsimagesMapper;
import com.smartcommunity.pojo.Complaints;
import com.smartcommunity.pojo.ComplaintsExample;
import com.smartcommunity.pojo.Complaintsimages;
import com.smartcommunity.pojo.Repair;
import com.smartcommunity.pojo.RepairExample;
import com.smartcommunity.pojo.Repairimages;
import com.smartcommunity.service.IComplaintsService;
import com.smartcommunity.util.ConstantPool;
import com.smartcommunity.util.DateUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.PathUtil;

import edu.hust.smartcommunity.paginator.domain.PageBounds;
import edu.hust.smartcommunity.paginator.domain.PageList;

public class ComplaintsServiceImpl implements IComplaintsService {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComplaintsServiceImpl.class);
	
	private ComplaintsMapper complaintsMapper;
	private ComplaintsimagesMapper complaintsimagesMapper;
	public ComplaintsimagesMapper getComplaintsimagesMapper() {
		return complaintsimagesMapper;
	}

	public void setComplaintsimagesMapper(
			ComplaintsimagesMapper complaintsimagesMapper) {
		this.complaintsimagesMapper = complaintsimagesMapper;
	}

	public ComplaintsMapper getComplaintsMapper() {
		return complaintsMapper;
	}

	public void setComplaintsMapper(ComplaintsMapper complaintsMapper) {
		this.complaintsMapper = complaintsMapper;
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject submitComplaints(Complaints complaints,
			java.util.List<File> images, List<String> imagesFileName,
			List<String> imagesContentType) {
		// TODO Auto-generated method stub
		System.out.println(complaints);
		JSONObject jsonObject = null;
		if (complaints == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "投诉信息为空");
			return jsonObject;
		}
		if (complaints.getTime() == null) {
			complaints.setTime(new java.util.Date());
		}
		System.out.println(complaints.getRoomnumber());
			complaintsMapper.insertSelective(complaints);


		jsonObject = JSONUtil.getJsonObject(true);
		Complaintsimages complaintsimages = new Complaintsimages();
	
		if (images == null) {
			jsonObject = JSONUtil.getJsonObject(true);
			return jsonObject;
		}

		for (int i = 0 ; i < images.size(); i ++) {
			String imagepath = PathUtil.getComplaintsPath(complaints.getRoomnumber()); 	// 图片保存路径

			String imagename = complaints.getId() + imagesFileName.get(i); 				// 图片的文件名
			String realpath = org.apache.struts2.ServletActionContext.
					getServletContext().getRealPath(imagepath);
			
			File saveFile = new File(new File(realpath), imagename);
			if (!saveFile.getParentFile().exists())
				saveFile.getParentFile().mkdirs();
			try {
				System.out.println(images.get(i).length());
				org.apache.commons.io.FileUtils.copyFile(images.get(i), saveFile);		// 保存文件 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// 如果保存文件失败则直接保存下一张
				logger.error(e.getMessage());
				continue;
			}
			// 将地址存入数据库中
			complaintsimages.setComplaintsid(complaints.getId());
			complaintsimages.setUrl(imagepath + imagename);

			complaintsimagesMapper.insertSelective(complaintsimages);
	
		}
		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject listComplaintsByPage(Integer pageNo,Integer pageSize,boolean isFinished,String roomNumber) {
		// TODO Auto-generated method stub
		if (pageSize == null) {
			pageSize = 10;
		}
		if (pageNo == null) {
			pageNo = 1;
		}
		PageBounds pageBounds = new PageBounds(pageNo,pageSize);
		JSONObject jsonObject = null;
		ComplaintsExample complaintsExample = new ComplaintsExample();
		ComplaintsExample.Criteria criteria = complaintsExample.or();
		if (roomNumber != null) {
			criteria.andRoomnumberLike(roomNumber);
		}
		if (isFinished == true) {
			criteria.andProcessedstateEqualTo(ConstantPool.STATE_COMPLETE);
		} else {
			List<Integer> stateIntegers = new java.util.ArrayList<>();
			stateIntegers.add(ConstantPool.STATE_PROCESSING);
			stateIntegers.add(ConstantPool.STATE_UNCOMPLETE);
			criteria.andProcessedstateIn(stateIntegers);
		}
		PageList<Complaints> complaintslist = null;

			complaintslist = complaintsMapper.selectComplaintsAndImages(complaintsExample,pageBounds);

		JSONArray jsonArray = (JSONArray) JSON.toJSON(complaintslist);
		
		jsonObject = com.smartcommunity.util.JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);

		JSONUtil.putTotalPage(jsonObject, complaintslist.getPaginator().getTotalPages());
		return jsonObject;
	}

	@Override
	public JSONObject cancelComplaints(Complaints complaints) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (complaints.getId() == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "投诉id 为空");
			return jsonObject;
		}
		ComplaintsExample complaintsExample = new ComplaintsExample();
		ComplaintsExample.Criteria criteria = complaintsExample.or();
		if (complaints.getRoomnumber() != null) {
			criteria.andRoomnumberEqualTo(complaints.getRoomnumber());
		}
		System.out.println(complaints.getRoomnumber());
		criteria.andComplaintsIdEqualTo(complaints.getId());
		java.util.List<Complaints> complaints2 = null;
		try {
			complaints2 = complaintsMapper.selectComplaintsAndImages(complaintsExample);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
			return jsonObject;
		}
		String roomnumber = null;
		if (complaints2.size() > 0) {
			complaints = complaints2.get(0);
			
			List<Complaintsimages> complaintsimages = complaints.getComplaintsimages();
			Complaintsimages tempComplaintsimages = null;
			for (int i = 0; i < complaintsimages.size(); i++) {
				tempComplaintsimages = complaintsimages.get(i);
				// 删除图片
			String realpath = org.apache.struts2.ServletActionContext.
					getServletContext().getRealPath(tempComplaintsimages.getUrl());

				File deleteFile = new File(realpath);
				if (deleteFile.exists()) {
					try {
						boolean state = deleteFile.delete();
						if (state == true) {
							// 如果删除图片成功，就删除图片地址
							try {
								complaintsimagesMapper.deleteByPrimaryKey(tempComplaintsimages.getId());
							} catch (Exception e) {
								// TODO 删除图片地址异常
								e.printStackTrace();
								jsonObject = JSONUtil.getJsonObject(false);
								JSONUtil.putCause(jsonObject, e);
								return jsonObject;
							} 
							continue;
						}
						else {
							jsonObject = JSONUtil.getJsonObject(false);
							JSONUtil.putCause(jsonObject, "delete image [" + tempComplaintsimages.getUrl() + "] failed" );
							return jsonObject;
						}
					} catch (Exception e) {
						// TODO 删除文件异常
						e.printStackTrace();
						jsonObject = JSONUtil.getJsonObject(false);
						JSONUtil.putCause(jsonObject, e);
						return jsonObject;
					}
				}
			} // 删除图片


			try {
				complaintsMapper.deleteByPrimaryKey(complaints.getId());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				jsonObject = JSONUtil.getJsonObject(false);
				JSONUtil.putCause(jsonObject, e);
				return jsonObject;
			}
		}	// 删除报修信息
		else {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有要删除的报修信息");
			return jsonObject;
		}
		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject submitFeedback(Integer id, String feedback) {

		JSONObject jsonObject = null;

		if (id == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "报修 id 为空");
			return jsonObject;
		}

		if (feedback == null || "".equals(feedback)) {

			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有要提交的反馈信息");
			return jsonObject;
		}
		
			complaintsMapper.updateFeedback(id,feedback);

		jsonObject = JSONUtil.getJsonObject(true);
		jsonObject.put("feedback",feedback);
		return jsonObject;
	}

}
