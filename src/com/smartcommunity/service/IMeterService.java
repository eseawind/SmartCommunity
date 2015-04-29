package com.smartcommunity.service;



import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Meter;

public interface IMeterService<T> {

	/**
	 * 提交抄表信息
	 * @param meters
	 * @return
	 */
	public JSONObject submitMeterInfo(java.util.List<Meter> meters);

/**
 * 查询抄表信息
 * @param metertype 表类型
 * @param roomnumber 房间号
 * @param pageNo
 * @param pageSize
 * @return
 */
	public JSONObject listMeterInfo(Integer metertype,String roomnumber,Integer pageNo,Integer pageSize);
	public JSONObject paying(Integer id,Float amount);
	public JSONObject getAccount(String roomNumber);

	public JSONObject list(T meterParams);
}
