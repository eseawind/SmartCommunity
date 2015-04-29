package com.smartcommunity.service;

import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.pojo.Userpass;



public interface IUserManageService {

	/**
	 * 提交业主信息，设置密码为身份证后六位，如果身份证号为空，密码为 000000
	 * @param roomowner
	 * @param roles 提交的角色
	 * @return
	 */
	public JSONObject submitRoomownerInfo(Roomowner roomowner,Set<Integer> roles);
	/**
	 * 用户登陆，
	 * @param username
	 * @param password
	 * @return 失败返回 null
	 */
	public JSONObject  login(String username, String password) ;
	/**
	 * 更改业主信息
	 * @param roomowner
	 * @return
	 */
	public JSONObject updateRoomownerInfo(Roomowner roomowner);
	
	/**
	 * 查询用户信息
	 * @param type 业主或租户，为空时查询全部
	 * @return
	 */
	public JSONObject listRoomownerInfo(Integer pageNo,Integer pageSize,Integer type,String roomnumberPattern);
	/**
	 * 删除用户
	 * @param roomownerid
	 * @return
	 */
	public JSONObject deleteRoomowner(String roomNo);

	/**
	 * 添加物业管理员帐户
	 * @param name
	 * @param password
	 * @return
	 */
	public JSONObject addManager(String name ,String password);
}
