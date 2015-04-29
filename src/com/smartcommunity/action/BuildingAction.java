package com.smartcommunity.action;

import java.io.InputStream;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.smartcommunity.pojo.Building;
import com.smartcommunity.pojo.Room;
import com.smartcommunity.service.IBuildingService;
import com.smartcommunity.util.ExceptionUtil;
import com.smartcommunity.util.InputStreamUtil;
import com.smartcommunity.util.JSONUtil;

public class BuildingAction extends BaseActionSupport<BuildingParams> {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(BuildingAction.class);
	private IBuildingService buildingService;
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public IBuildingService getBuildingService() {
		return buildingService;
	}

	public void setBuildingService(IBuildingService buildingService) {
		this.buildingService = buildingService;
	}

	/**
	 * 完成
	 * 
	 * @return
	 */
	public String submitBuildingInfo() {
		JSONObject jsonObject = new JSONObject();
		if ((jsonObject = parameters.checkBuildingInsert()) != null) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		jsonObject = null;

		try {
			jsonObject = buildingService.submitBuildingInfo(parameters
					.getBuilding());
		} catch (DuplicateKeyException e) {
			logger.error(ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "楼宇编号已经存在");
		}

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	/**
	 * 完成
	 * 
	 * @return
	 */
	public String updateBuildingInfo() {
		JSONObject jsonObject = parameters.checkBuildingUpdate();

		if (jsonObject != null) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		try {
			jsonObject = buildingService.updateBuildingInfo(parameters
					.getBuilding());
		} catch (DataIntegrityViolationException e) {

			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "楼宇编号冲突");
		} catch (BadSqlGrammarException e) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有要更新的信息");
		}

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	/**
	 * 完成 查询楼宇信息，可以根据楼宇编号查询
	 * 
	 * @return
	 */
	public String listBuildingInfoByPage() {
		JSONObject jsonObject = null;

		jsonObject = buildingService.listBuildingInfoByPage(
				parameters.getPageNo(), parameters.getPageSize(),
				parameters.getBuildNo());

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	public String deleteBuildingInfo() {
		JSONObject jsonObject = null;

		try {
			jsonObject = buildingService.deleteBuildingInfo(parameters
					.getBuildNo());
		} catch (DataIntegrityViolationException e) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "请先删除房间信息");
		}

		inputStream = InputStreamUtil.getInputStream(jsonObject);

		return SUCCESS;
	}

	/** 提交房间信息 */
	public String submitRoomInfo() {
		JSONObject jsonObject = null;
		if ((jsonObject = parameters.checkRoomInsert()) != null) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		try {
			jsonObject = buildingService.submitRoomInfo(parameters.getRoom());
		} catch (DataIntegrityViolationException e) {
			// TODO Auto-generated catch block
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "房间号已存在或楼栋号不存在");
		}
		inputStream = com.smartcommunity.util.InputStreamUtil
				.getInputStream(jsonObject);
		return SUCCESS;
	}

	/** 更新房间信息 */
	public String updateRoomInfo() {
		JSONObject jsonObject = parameters.checkRoomUpdate();

		if (jsonObject != null) {
			inputStream = InputStreamUtil.getInputStream(jsonObject);
			return SUCCESS;
		}
		try {
			jsonObject = buildingService.updateRoomInfo(parameters.getRoom());
		} catch (DataIntegrityViolationException e) {
			// TODO Auto-generated catch block
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "房间号已存在或楼栋号不存在");
		} catch (BadSqlGrammarException e) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有信息要更新");
		}

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	/** 查询房间信息 */
	public String listRoomInfoByPage() {

		JSONObject jsonObject = null;
		jsonObject = buildingService.listRoomInfoByPage(parameters.getPageNo(),
				parameters.getPageSize(),
				parameters.getRoomnumberSearchPattern());

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public String listRoomInfoByCondition() {

		JSONObject jsonObject = null;
		jsonObject = buildingService.listRoomInfoByCondition(parameters.getPageNo(),
				parameters.getPageSize(),
				parameters.getRoom());

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	/** 删除房间信息 */
	public String deleteRoomInfo() {
		JSONObject jsonObject = null;

		try {
			jsonObject = buildingService.deleteRoomInfo(parameters
					.getRoomNumber());
		} catch (DataIntegrityViolationException e) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "请先删除住户信息");
		}

		inputStream = InputStreamUtil.getInputStream(jsonObject);

		return SUCCESS;
	}

	public String listBuildingNumber() {
		JSONObject jsonObject = null;

		jsonObject = buildingService.listBuildingNumber();

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}

	public String listUnitNumber() {
		JSONObject jsonObject = null;

			jsonObject = buildingService.listUnitNumber(parameters.getBuildNo());

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
	public String listRoomNumber() {
		JSONObject jsonObject = null;

			jsonObject = buildingService.listRoomNumber(parameters.getBuildNo(), parameters.getUnitNo());

		inputStream = InputStreamUtil.getInputStream(jsonObject);
		return SUCCESS;
	}
}
