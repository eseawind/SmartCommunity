package com.smartcommunity.service;

import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Decorate;
import com.smartcommunity.pojo.Decoratefee;

public interface IDecorateService {

	public JSONObject submitDecorate(Decorate decorate);
	public JSONObject submitDecoratefee(Decoratefee decorate);
	public JSONObject listDecorate(String roomnumber,Integer pageNo,Integer pageSize);
	public JSONObject listDecoratefee(String roomnumber,Integer pageNo,Integer pageSize);
}
