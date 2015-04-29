package com.smartcommunity.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;









import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.interceptor.Page;
import com.smartcommunity.mapper.RepairMapper;
import com.smartcommunity.mapper.RepairimagesMapper;
import com.smartcommunity.pojo.ComplaintsExample;
import com.smartcommunity.pojo.Repair;
import com.smartcommunity.pojo.RepairExample;
import com.smartcommunity.pojo.Repairimages;
import com.smartcommunity.service.IRepairService;
import com.smartcommunity.util.ConstantPool;
import com.smartcommunity.util.DateUtil;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.PathUtil;

import edu.hust.smartcommunity.paginator.domain.PageBounds;
import edu.hust.smartcommunity.paginator.domain.PageList;

public class RepairServiceImpl implements IRepairService {
	
	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RepairServiceImpl.class);
	private RepairMapper repairMapper;
	private RepairimagesMapper repairimagesMapper;
	public RepairimagesMapper getRepairimagesMapper() {
		return repairimagesMapper;
	}
	public void setRepairimagesMapper(RepairimagesMapper repairimagesMapper) {
		this.repairimagesMapper = repairimagesMapper;
	}
	public RepairMapper getRepairMapper() {
		return repairMapper;
	}
	public void setRepairMapper(RepairMapper repairMapper) {
		this.repairMapper = repairMapper;
	}
	@Override
	public JSONObject listAll() {
		// TODO Auto-generated method stub

		List<Repair> repairs = repairMapper.selectByExample(new RepairExample());
		JSONArray jsonArray = new JSONArray();
		for (Repair repairInfo : repairs) {

			JSONObject jsonObject = (JSONObject) JSON.toJSON(repairInfo);
			
//			jsonObject.put("repairid", repairInfo.getId());
//			jsonObject.put("username", repairInfo.getUsername());
//			jsonObject.put("name", repairInfo.getUser().getName());
//			jsonObject.put("devicename",repairInfo.getDevicename());
//			jsonObject.put("description",repairInfo.getDescription());		
			
			String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
					format(repairInfo.getDate());
			jsonObject.put("date", com.smartcommunity.util.DateUtil.dateToString(repairInfo.getDate()));
			jsonArray.add(jsonObject);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(JSONUtil.successString, true);
		JSONUtil.putResult(jsonObject, jsonArray);
		return jsonObject;
	}
	@Override
	public JSONArray listAllUnprocessed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject submitRepairInfo(Repair repair,
			java.util.List<File> images,List<String> imagesFileName, List<String> imagesContentType) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (repair == null) {

			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "报修信息不能为空");
			return jsonObject;
		}
			/** 先插入报修的文字信息 */
			repairMapper.insertSelective(repair);
		/** 如果插入成功则保存图片 */
		Repairimages repairimages = new Repairimages();

		if (images == null) {
			jsonObject = JSONUtil.getJsonObject(true);
			return jsonObject;
		}

		for (int i = 0 ; i < images.size(); i ++) {
			String imagepath = PathUtil.getRepairPath(repair.getRoomnumber()) ; 	// 图片保存路径
			String imagename = repair.getId() + imagesFileName.get(i); 				// 图片的文件名
			String realpath = org.apache.struts2.ServletActionContext.
					getServletContext().getRealPath(imagepath);
			System.out.println(realpath);
			/** 插入图片地址到数据库 */
			repairimages.setRepairid(repair.getId());
			repairimages.setUrl(imagepath + imagename);

				repairimagesMapper.insertSelective(repairimages);

			/** 保存文件 */
			File saveFile = new File(new File(realpath), imagename);
			if (!saveFile.getParentFile().exists())
				saveFile.getParentFile().mkdirs();
				try {
					org.apache.commons.io.FileUtils.copyFile(images.get(i), saveFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException("保存文件 " + saveFile.getPath() + "/" + saveFile.getName() + " 失败 : " + e.getMessage() );
				}		// 保存文件 
		
		}
		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}
	@Override
	public boolean setProcessed(Repair repair) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public JSONObject findUserRepairInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public JSONObject submitFeedback(Repair repair) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		System.out.println(repair);
		Integer id = repair.getId();
		String feedback = repair.getFeedback();
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
		// 重新修改的报修信息，防止更改其它字段
		repair = new Repair();
		repair.setId(id);
		repair.setFeedback(feedback);

			repairMapper.updateByPrimaryKeySelective(repair);

		jsonObject = JSONUtil.getJsonObject(true);
		jsonObject.put("feedback", repair.getFeedback());
		return jsonObject;
	}
	@Override
	public JSONObject listRepariInfoByPage(Integer pageNo, Integer pageSize, boolean isFinished,String roomnumber) {
		// TODO Auto-generated method stub
		// 如果为空则选用默认值
		if (pageSize == null) {
			pageSize = 10;
		}
		if (pageNo == null) {
			pageNo = 1;
		}
//		// 设置分页信息
//		Page<Repair> page = new Page<Repair>();
//		page.setPageSize(pageSize);
//		page.setPageNo(pageNo);
		
		PageBounds pageBounds = new PageBounds(pageNo,pageSize,true);
		// 设置查询条件
		RepairExample repairExample = new RepairExample();
		RepairExample.Criteria criteria = repairExample.or();
		if (roomnumber != null) {
			criteria.andRoomnumberLike(roomnumber);
		}
		if (isFinished == true) {
			criteria.andProcessedstateEqualTo(ConstantPool.STATE_COMPLETE);
		} else {
			List<Integer> states = new java.util.ArrayList<>();
			states.add(ConstantPool.STATE_PROCESSING);
			states.add(ConstantPool.STATE_UNCOMPLETE);
			criteria.andProcessedstateIn(states);
		}
		
		java.util.Map<String, Object> map = new java.util.HashMap<String, Object>();
	//	map.put("page", page);

	//	map.put("example",repairExample);
	//	map.put("state", 2);
		PageList<Repair> repairs;
		JSONObject jsonObject = new JSONObject();
		try {
			repairs = repairMapper.selectRepairAndImages(repairExample,pageBounds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		//	logger.error(e.getMessage());
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
			return jsonObject;
		}
		JSONArray jsonArray = (JSONArray) JSON.toJSON(repairs);


		jsonObject = com.smartcommunity.util.JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, repairs.getPaginator().getTotalPages());
		return jsonObject;
	}
	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject cancelRepair(Repair repair) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (repair.getId() == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "报修 id 为空");
			return jsonObject;
		}
		RepairExample repairExample = new RepairExample();

		RepairExample.Criteria criteria = repairExample.or();
		if (repair.getRoomnumber() != null) {
			criteria.andRoomnumberEqualTo(repair.getRoomnumber());
		}
		criteria.andRepairIdEqualTo(repair.getId());

		java.util.ArrayList<Repair> repairs = null;

			repairs = repairMapper.selectRepairAndImages(repairExample);


		if (repairs.size() > 0) {

			repair = repairs.get(0);

			List<Repairimages> repairimages = repair.getRepairimages();
			Repairimages tempRepairimages = null;

			for (int i = 0; i < repairimages.size(); i++) {
				tempRepairimages = repairimages.get(i);

				System.out.println(tempRepairimages.getId());
				// 删除图片
			String realpath = org.apache.struts2.ServletActionContext.
					getServletContext().getRealPath(tempRepairimages.getUrl());
			
				File deleteFile = new File(realpath);
				/** 删除 文件  */
				if (deleteFile.exists()) {
						boolean state = deleteFile.delete();
					
				}
				/** 删除文件地址 */
				repairimagesMapper.deleteByPrimaryKey(tempRepairimages.getId());
			} // 删除图片
				/** 删除报修信息 */
				repairMapper.deleteByPrimaryKey(repair.getId());

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
	public JSONObject setState(Integer repairid, Integer state) {
		// TODO Auto-generated method stub
		Repair repair = new Repair();
		JSONObject jsonObject = null;
		
		if (repairid == null || state == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "参数错误");
			return jsonObject;
		}
		repair.setId(repairid);
		repair.setProcessedstate(state);
		if (state == ConstantPool.STATE_COMPLETE) {
			repair.setProcessedtime(new java.util.Date());
		}
		repairMapper.updateByPrimaryKeySelective(repair);
		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

//	@Override
//	public net.sf.json.JSONArray listAllUnprocessed() {
//		// TODO Auto-generated method stub
//		String hql = "from RepairInfo as r where r.isprocessed=0 order by r.date desc";
//		List<RepairInfo> repairInfos = repairInfoDao.find(hql); 
//		net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
//		for (RepairInfo repairInfo : repairInfos) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("repairid", repairInfo.getId());
//			jsonObject.put("username", repairInfo.getUsername());
//			jsonObject.put("name", repairInfo.getUser().getName());
//			jsonObject.put("devicename",repairInfo.getDevicename());
//			jsonObject.put("description",repairInfo.getDescription());	
//			jsonObject.put("telephone",repairInfo.getPhone());
//			jsonObject.put("address",repairInfo.getAddress());
//			String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
//					format(repairInfo.getDate());
//			jsonObject.put("date", date);
//			jsonArray.add(jsonObject);
//		}
//		JSONObject jsonObject = new JSONObject();
//		//jsonObject.put("success", true);
//		///jsonObject.put("result", jsonArray);
//
//		return jsonArray;
//	}
//
//	@Override
//	public net.sf.json.JSONArray listAllProcessed() {
//		// TODO Auto-generated method stub
//		String hql = "from RepairInfo as r where r.isprocessed=1 order by r.date desc";
//		List<RepairInfo> repairInfos = repairInfoDao.find(hql); 
//		net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
//		for (RepairInfo repairInfo : repairInfos) {
//			JSONObject jsonObject = new JSONObject();
//			jsonObject.put("repairid", repairInfo.getId());
//			jsonObject.put("username", repairInfo.getUsername());
//			jsonObject.put("name", repairInfo.getUser().getName());
//			jsonObject.put("devicename",repairInfo.getDevicename());
//			jsonObject.put("description",repairInfo.getDescription());		
//			jsonObject.put("telephone",repairInfo.getPhone());
//			jsonObject.put("address",repairInfo.getAddress());
//			String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
//					format(repairInfo.getDate());
//			jsonObject.put("date", date);
//			jsonArray.add(jsonObject);
//		}
//		JSONObject jsonObject = new JSONObject();
//		//jsonObject.put("success", true);
//		//jsonObject.put("result", jsonArray);
//
//		return jsonArray;
//	}
//
//	public com.sys.dao.RepairInfoDao getRepairInfoDao() {
//		return repairInfoDao;
//	}
//
//	public void setRepairInfoDao(com.sys.dao.RepairInfoDao repairInfoDao) {
//		this.repairInfoDao = repairInfoDao;
//	}
//
//	@Override
//	public boolean saveRepairInfo(RepairInfo repairInfo) {
//		// TODO Auto-generated method stub
//		System.out.println("save repair info");
//		//System.out.println(JSONObject.fromObject(repairInfo).toString());
//		repairInfoDao.save(repairInfo);
//		return true;
//	}
//	public boolean setProcessed(RepairInfo repairInfo) {
//		System.out.println("setProcessed");
//		repairInfoDao.update(repairInfo);
//		return true;
//	}
//
//	public JSONObject findUserRepairInfo(com.sys.entity.User user) {
//		String hql = "from RepairInfo as r where r.user=? order by r.date desc";
//		List<RepairInfo> repairInfos = repairInfoDao.find(hql,user);
//		net.sf.json.JSONArray jsonArray = new net.sf.json.JSONArray();
//		String phone = null;
//		String address = null;	
//		for (RepairInfo repairInfo : repairInfos) {
//			//repairInfo.getUser().setRoles(null);
//			JSONObject jsonObject = new JSONObject();
//			if (phone == null) {
//				phone = repairInfo.getPhone();
//			}
//			if (address == null) {
//				address = repairInfo.getAddress();
//			}
//			jsonObject.put("devicename", repairInfo.getDevicename());
//			jsonObject.put("description", repairInfo.getDescription());
//			String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").
//					format(repairInfo.getDate());
//			jsonObject.put("date", date);
//			if (repairInfo.getIsprocessed()) {
//				jsonObject.put("state", "已处理");
//			} else {
//				jsonObject.put("state", "未处理");
//			}
//			jsonArray.add(jsonObject);
//		}
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("success", true);
//		if (phone != null) {
//			jsonObject.put("phone", phone);
//		}		
//		if (address != null) {
//			jsonObject.put("address", address);
//		}
//		jsonObject.put("result", jsonArray);
//	//	System.out.println(jsonObject.toString());
//		return jsonObject;
//	}
//
//	@Override
//	public JSONObject findEqual(Integer id) {
//		// TODO Auto-generated method stub
//
//		String hql = "from RepairInfo as r where r.id=? order by r.date desc";
//		List<RepairInfo> repairInfos = repairInfoDao.find(hql,id);
//		net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
//		for (RepairInfo repairInfo : repairInfos) {
//			//repairInfo.getUser().setRoles(null);
//			jsonObject = new JSONObject();
//			jsonObject.put("address", repairInfo.getAddress());
//			jsonObject.put("phone", repairInfo.getPhone());
//
//			break;
//		}
//		jsonObject.put("success", true);
//
//		System.out.println(jsonObject.toString());
//		return jsonObject;
//	}
//
//	@Override
//	public boolean submitRepairInfo(Repair repair) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean setProcessed(Repair repair) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public JSONObject findUserRepairInfo() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
