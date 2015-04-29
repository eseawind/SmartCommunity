/**
 * 
 */
package com.smartcommunity.service;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Device;



/**
 * @version 创建时间:2015年1月6日
 * @author Huynh
 */
public interface IDeviceService {
	/**
	 * 获取所有报修设备名称
	 * @version 创建时间: 2015年1月6日
	 * @author Huynh
	 * @return
	 */
	public JSONObject getDevice(Integer id);

	/**
	 * 管理员增加设备
	 * @version 创建时间: 2015年1月6日
	 * @author Huynh
	 * @return
	 */
	public int addDevice();
	/**
	 * 获取设备错误信息
	 * @author yang
	 * @param id
	 * @return
	 */
	public JSONObject getDeviceFault(Integer id);
}
