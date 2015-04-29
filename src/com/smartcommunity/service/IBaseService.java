package com.smartcommunity.service;

import com.alibaba.fastjson.JSONObject;

public interface IBaseService<T> {

	public JSONObject doQuery(T params);
	public JSONObject doUpdate(T params);
	public JSONObject doDelete(T params);
	public JSONObject doInsert(T params);

}
