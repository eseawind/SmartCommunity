package com.smartcommunity.action;

import java.util.ArrayList;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Meter;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.UTIL;


public class MeterParams {

	private Integer buildNo; // 楼宇号
	private Integer unitNo; // 单元号
	private Integer roomNo; // 房间号
	private Float waterconsum; // 水量
	private Float elecconsum; // 电量
	private String readman; // 抄表人
	private Date readtime; // 抄表 时间
	private Integer metertype; // 表计类型
	private Integer pageNo; 	//页号

	private String method; // 
	private Integer pageSize; // 页大小
	private Integer id; // 
	private Float amount; // 缴纳的金额
	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
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
	public String getRoomnumberSearchPattern() {
		return UTIL.getRoomnumberSearchPattern(buildNo, unitNo, roomNo);
	}
	public String getRoomnumber() {
		return UTIL.getRoomnumber(buildNo, unitNo, roomNo);
	}


	public Float getWaterconsum() {
		return waterconsum;
	}

	public void setWaterconsum(Float waterconsum) {
		this.waterconsum = waterconsum;
	}

	public Float getElecconsum() {
		return elecconsum;
	}

	public void setElecconsum(Float elecconsum) {
		this.elecconsum = elecconsum;
	}

	public String getReadman() {
		return readman;
	}

	public void setReadman(String readman) {
		this.readman = readman;
	}

	public Date getReadtime() {
		if (readtime == null) {
			readtime = new Date();
		}
		return readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	/**
	 * 检查参数的正确性
	 * @param jsonObject 如果参数有错误，则返回错误信息
	 * @return 
	 */
	public boolean checkParameters(JSONObject jsonObject) {
		/** 检查房间号是否正确 */
		String roomnumber = UTIL.getRoomnumber(buildNo, unitNo, roomNo);
		if (jsonObject != null) {
			JSONUtil.putSuccess(jsonObject, false);
		}
//		if (roomnumber == null) {
//
//			if (jsonObject != null) {
//				JSONUtil.putCause(jsonObject, "房间号不能为空");
//			}
//			return false;
//		}
		if (roomnumber != null && ! roomnumber.matches(UTIL.roomnumberPattern)) {
			if (jsonObject != null) {
				JSONUtil.putCause(jsonObject, "房间号格式不匹配");
			}
			return false;
		}
		// 检查用量是否正确

		if (waterconsum == null && elecconsum == null) {
			if (jsonObject != null) {
				JSONUtil.putCause(jsonObject, "用量不能为空");
			}
			return false;
		}
		if (waterconsum != null && waterconsum < 0 || elecconsum != null && elecconsum < 0) {
			if (jsonObject != null) {
				JSONUtil.putCause(jsonObject, "用量不能为负值");
			}
			return false;
		}
		return true;
	}

	public boolean checkPament(JSONObject jsonObject) {
		if ( jsonObject == null) {
			jsonObject = JSONUtil.getJsonObject(false);
		}
		JSONUtil.putSuccess(jsonObject, false);
		if (id == null) {
			JSONUtil.putCause(jsonObject, "请选择要支付的条目");
			return false;
		}
		if (amount == null) {
			JSONUtil.putCause(jsonObject, "请输入支付的金额");
			return false;			
		}
		if (amount < 0 ) {
			JSONUtil.putCause(jsonObject, "输入的支付金额不合法");
			return false;						
		}
		return true;
	}
	public Integer getMetertype() {
		return metertype;
	}

	public void setMetertype(Integer metertype) {
		this.metertype = metertype;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public java.util.List<Meter> getMeterInfo() {
		String roomnumber = UTIL.getRoomnumber(buildNo, unitNo, roomNo);
		java.util.List<Meter> meters = new ArrayList<Meter>();
		Meter meter = null;
		Float dataconsum = null;
		if (waterconsum != null) {
			meter = new Meter();
			meter.setRoomnumber(roomnumber);
			meter.setMetertype(0); // 水量 

			meter.setReadingtime(getReadtime());
			meter.setReadman(readman);
			meter.setReaddata(waterconsum );
			meter.setVersion(1);
			meters.add(meter);
		}

		if (elecconsum != null) {
			meter = new Meter();
			meter.setRoomnumber(roomnumber);
			meter.setMetertype(1);

			meter.setReadingtime(getReadtime());
			meter.setReadman(readman);
			meter.setReaddata(elecconsum);
			meter.setVersion(1);
			meters.add(meter);
		}
		return meters;
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
