/**
 * 
 */
package com.smartcommunity.service.impl;

import java.util.List;






import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.mapper.DeviceMapper;
import com.smartcommunity.mapper.DevicefaultMapper;
import com.smartcommunity.pojo.Device;
import com.smartcommunity.pojo.DeviceExample;
import com.smartcommunity.pojo.Devicefault;
import com.smartcommunity.pojo.DevicefaultExample;
import com.smartcommunity.service.IDeviceService;
import com.smartcommunity.util.JSONUtil;

/**
 * @version 创建时间:2015年1月6日
 * @author Huynh
 */
public class DeviceServiceImpl implements IDeviceService{

	private DeviceMapper deviceMapper;
	private DevicefaultMapper devicefaultMapper;
	public DevicefaultMapper getDevicefaultMapper() {
		return devicefaultMapper;
	}

	public void setDevicefaultMapper(DevicefaultMapper devicefaultMapper) {
		this.devicefaultMapper = devicefaultMapper;
	}

	public DeviceMapper getDeviceMapper() {
		return deviceMapper;
	}

	public void setDeviceMapper(DeviceMapper deviceMapper) {
		this.deviceMapper = deviceMapper;
	}

	/* (non-Javadoc)
	 * @see com.smartcommunity.service.IDeviceService#getDevice()
	 */
	@Override
	public JSONObject getDevice(Integer id) {
		JSONObject jsonObject =  null;	
		if (id == null) {
			List<Device> devices = deviceMapper.selectByExample(new DeviceExample());
			if (devices == null) {
				return null;
			}
			jsonObject = JSONUtil.getJsonObject(true);
			JSONArray jsonArray = (JSONArray) JSON.toJSON(devices);

			JSONUtil.putResult(jsonObject, jsonArray);
		}else {
			Device device = deviceMapper.selectByPrimaryKey(id);
			if (device == null) {
				return null;
			}

			jsonObject = JSONUtil.getJsonObject(true);
		//	JSONUtil.putResult(jsonObject, JSON.toJSON(device));
			jsonObject.put("id", device.getId());
			jsonObject.put("devicename", device.getDevicename());
		}
		return jsonObject;
	}

	/* (non-Javadoc)
	 * @see com.smartcommunity.service.IDeviceService#addDevice()
	 */
	@Override
	public int addDevice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public JSONObject getDeviceFault(Integer id) {
		// TODO Auto-generated method stub
		if (id == null) {
			JSONObject jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有输入要查询的设备");
			return jsonObject;
		}
		DevicefaultExample example = new DevicefaultExample();
		example.or().andDeviceidEqualTo(id);
		try {
			List<Devicefault> devicefaults = devicefaultMapper.selectByExample(example);
			JSONObject jsonObject = new JSONObject();
			if (devicefaults != null) {
				JSONArray jsonArray = new JSONArray();
				for (Devicefault devicefault : devicefaults) {
					jsonObject = (JSONObject) com.alibaba.fastjson.JSON.toJSON(devicefault);
					jsonArray.add(jsonObject);
				}
				jsonObject = JSONUtil.getJsonObject(true);
				JSONUtil.putResult(jsonObject, jsonArray);
			}
			return jsonObject;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JSONObject jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
			return jsonObject;
		}
	}

}
