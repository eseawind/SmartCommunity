package com.smartcommunity.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.mapper.AccountMapper;
import com.smartcommunity.mapper.ResourcesMapper;
import com.smartcommunity.mapper.RoomownerMapper;
import com.smartcommunity.mapper.UserpassMapper;
import com.smartcommunity.mapper.UserrolejoinMapper;
import com.smartcommunity.pojo.Account;
import com.smartcommunity.pojo.Resources;
import com.smartcommunity.pojo.ResourcesExample;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.pojo.RoomownerExample;
import com.smartcommunity.pojo.Userpass;
import com.smartcommunity.pojo.UserpassExample;
import com.smartcommunity.pojo.Userrolejoin;
import com.smartcommunity.pojo.UserrolejoinExample;
import com.smartcommunity.service.IUserManageService;
import com.smartcommunity.util.JSONUtil;
import com.smartcommunity.util.RandomUtil;
import com.smartcommunity.util.UTIL;

public class UserManageServiceImpl implements IUserManageService {

	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UserManageServiceImpl.class);
	private RoomownerMapper roomownerMapper;
	private UserpassMapper userpassMapper;
	private UserrolejoinMapper userrolejoinMapper;
	private ResourcesMapper resourcesMapper;
	private AccountMapper accountMapper;
	
	
	public ResourcesMapper getResourcesMapper() {
		return resourcesMapper;
	}

	public void setResourcesMapper(ResourcesMapper resourcesMapper) {
		this.resourcesMapper = resourcesMapper;
	}

	public UserpassMapper getUserpassMapper() {
		return userpassMapper;
	}

	public void setUserpassMapper(UserpassMapper userpassMapper) {
		this.userpassMapper = userpassMapper;
	}

	public UserrolejoinMapper getUserrolejoinMapper() {
		return userrolejoinMapper;
	}

	public void setUserrolejoinMapper(UserrolejoinMapper userrolejoinMapper) {
		this.userrolejoinMapper = userrolejoinMapper;
	}

	public RoomownerMapper getRoomownerMapper() {
		return roomownerMapper;
	}

	public void setRoomownerMapper(RoomownerMapper roomownerMapper) {
		this.roomownerMapper = roomownerMapper;
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject submitRoomownerInfo(Roomowner roomowner,Set<Integer> roles) {
		JSONObject jsonObject = null;
		if (roomowner == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "提交的信息为空");
			return jsonObject;
		}
		
		/** 插入用户名与密码,房间号为用户名 ,密码为身份证后六位*/ 
		Userpass userpass = new Userpass();
		userpass.setName(roomowner.getRoomnumber());
		userpass.setSalt(RandomUtil.getRandomString(0));
		
		// 身份证号后六位为密码
		String userIdentity = roomowner.getIdentity();

		StringBuilder passwordString = new StringBuilder();
		if (userIdentity == null) {
			passwordString.append(UTIL.defaultPassowrd); // 没有身份证号的默认密码
		} else {
			passwordString.append(userIdentity.substring(userIdentity.length() -6));
		}
		// 身份证后六位 + salt
		passwordString.append(userpass.getSalt());
		// 密码加密 
		userpass.setPassword(UTIL.MD5Encrypt(passwordString.toString()));
		userpassMapper.insertSelective(userpass);
		
		/** 插入用户角色关联信息 */
		// 如果角色为空，则分配业主角色
		if (roles == null) {
			roles = new java.util.HashSet<Integer>();
			roles.add(UTIL.roleuser);
		}
		java.util.List<Userrolejoin> userrolejoins = new java.util.ArrayList<>();
		Userrolejoin userrolejoin = null;
		for (Integer role : roles) {
			userrolejoin = new Userrolejoin();
			userrolejoin.setRoleid(role);
			userrolejoin.setUserpassid(userpass.getId());
			userrolejoins.add(userrolejoin);
		}
		// 插入多条
		userrolejoinMapper.insertMulti(userrolejoins);

		/** 插入业主具体信息 */
		roomowner.setUserpassid(userpass.getId());
		roomownerMapper.insertSelective(roomowner);
		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject login(String username, String password) {
		// TODO Auto-generated method stub
		if (username == null || password == null) {
			return null;
		}
		UserpassExample userpassExample = new UserpassExample();
		userpassExample.or().andNameEqualTo(username);
	
		List<Userpass> userpasses = userpassMapper.selectUserpassAndRolesByExample(userpassExample);
		Userpass loginUserpass = null;
		StringBuilder stringBuilder = new StringBuilder();
		for (Userpass userpass : userpasses) {
			stringBuilder.delete(0, stringBuilder.length());
			stringBuilder.append(password + userpass.getSalt());
			// 密码相同
			if (UTIL.MD5Encrypt(stringBuilder.toString()).equals(userpass.getPassword())) {
				loginUserpass = userpass;
				break;
			}
		}
		if (loginUserpass == null) {
			return null;
		}	
		/** 成功登陆后查询用户信息 */
		RoomownerExample roomownerExample = new RoomownerExample();
		roomownerExample.or().andUserpassidEqualTo(loginUserpass.getId());

		List<Roomowner> roomowners = roomownerMapper.selectByExample(roomownerExample);
		JSONObject jsonObject = JSONUtil.getJsonObject(true);

		if (roomowners != null && roomowners.size() > 0) {
			jsonObject.put("roomowner", roomowners.get(0));
		}

		/** 返回用户角色信息 */
		Set<Userrolejoin> userrolejoins = loginUserpass.getRoles();
		Set<Integer> roles = new java.util.HashSet<>();
		for (Userrolejoin userrolejoin : userrolejoins) {
			roles.add(userrolejoin.getRoleid());
		}
		jsonObject.put(UTIL.sessionRoles, roles);
//		ResourcesExample resourcesExample = new ResourcesExample();
//		resourcesExample.or().andRoleidIn(new ArrayList(roles));
//		List<Resources> resources = resourcesMapper.selectByExample(resourcesExample);
//		Set<String> resourceSet = new java.util.HashSet<>();
//		for (Resources res : resources) {
//			resourceSet.add(res.getUrl());
//		}
		return jsonObject;
	}

	@Override
	public JSONObject updateRoomownerInfo(Roomowner roomowner) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (roomowner == null || roomowner.getId() == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "更新参数不能为空");
		}
		roomownerMapper.updateByPrimaryKeySelective(roomowner);
		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject listRoomownerInfo(Integer pageNo,Integer pageSize,Integer type,String roomnumberPattern) {
		// TODO Auto-generated method stub
		if (pageNo == null){
			pageNo = 1;
		}	
		if (pageSize == null){
			pageSize = 10;
		}
		edu.hust.smartcommunity.paginator.domain.PageBounds pageBounds = new edu.hust.smartcommunity.paginator.domain.PageBounds(pageNo,pageSize);
		
		RoomownerExample roomownerExample = new RoomownerExample();
		RoomownerExample.Criteria criteria = roomownerExample.or();
		if (type != null) {
			criteria.andTypeEqualTo(type);
		}
		if (roomnumberPattern != null) {
			criteria.andRoomnumberLike(roomnumberPattern);
		}
		roomownerExample.setOrderByClause("roomnumber asc");
		edu.hust.smartcommunity.paginator.domain.PageList<Roomowner> roomowners = roomownerMapper.selectByExample(roomownerExample,pageBounds);
		JSONArray jsonArray = (JSONArray) JSON.toJSON(roomowners);
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, roomowners.getPaginator().getTotalPages());
		return jsonObject;
	}
	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject deleteRoomowner(String roomNo) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (roomNo == null) {
			jsonObject = JSONUtil.getJsonObject(false	);
			JSONUtil.putCause(jsonObject, "要删除的用户房间号空");
			return jsonObject;
		}
		/** 查询帐户是否为空 */
		com.smartcommunity.pojo.AccountExample accountExample = new com.smartcommunity.pojo.AccountExample();
		accountExample.or().andRoomnumberEqualTo(roomNo);
		List<Account> accounts = accountMapper.selectByExample(accountExample);
		if (accounts.size() > 0) {
			Account account = accounts.get(0);
			if (account.getRemain() > 0) {
				jsonObject = JSONUtil.getJsonObject(false);
				JSONUtil.putCause(jsonObject, "用户帐户不为空");
				return jsonObject;
			}else {
				accountMapper.deleteByPrimaryKey(account.getId());
			}
		}
		
		RoomownerExample roomownerExample = new RoomownerExample();
		roomownerExample.or().andRoomnumberEqualTo(roomNo);
		List<Roomowner> roomowners = roomownerMapper.selectByExample(roomownerExample);
		if (roomowners.size() <= 0) {
			jsonObject = JSONUtil.getJsonObject(false	);
			JSONUtil.putCause(jsonObject, "没有[" + roomNo +"]的信息");
			return jsonObject;
		}
		Roomowner roomowner = roomowners.get(0);
		Integer userpassid = roomowner.getUserpassid();
		if (userpassid != null) { 
			/** 删除用户角色关联 */
			UserrolejoinExample userrolejoinExample = new UserrolejoinExample();
			userrolejoinExample.or().andUserpassidEqualTo(userpassid);
			userrolejoinMapper.deleteByExample(userrolejoinExample);
			/** 删除用户密码 */
			userpassMapper.deleteByPrimaryKey(userpassid);
		}
		/** 删除用户信息 */
		roomownerMapper.deleteByPrimaryKey(roomowner.getId());
		jsonObject = JSONUtil.getJsonObject(true);

		return jsonObject;
	}

	@Override
	public JSONObject addManager(String name, String password) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (name == null || "".equals(name) || password == null || "".equals(password)) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "用户名与密码不能为空");
			return jsonObject;
		}
		Userpass userpass = new Userpass();
		userpass.setName(name);
		userpass.setSalt(RandomUtil.getRandomString(0));
		userpass.setPassword(UTIL.MD5Encrypt(password + userpass.getSalt()));
		userpassMapper.insertSelective(userpass);
		
//		Set<Integer> roleSet = new java.util.HashSet<>();
//		roleSet.add(1);

		java.util.List<Userrolejoin> userrolejoins = new java.util.ArrayList<>();
		Userrolejoin userrolejoin = new Userrolejoin();
			userrolejoin = new Userrolejoin();
			userrolejoin.setRoleid(UTIL.rolemanager);
			userrolejoin.setUserpassid(userpass.getId());
			userrolejoins.add(userrolejoin);
		// 插入多条
		userrolejoinMapper.insertMulti(userrolejoins);
		jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	public AccountMapper getAccountMapper() {
		return accountMapper;
	}

	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}


}
