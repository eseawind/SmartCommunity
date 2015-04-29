package com.smartcommunity.service.impl;

import java.util.List;

import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.pojo.Decorate;
import com.smartcommunity.pojo.DecorateExample;
import com.smartcommunity.pojo.Decoratefee;
import com.smartcommunity.pojo.DecoratefeeExample;
import com.smartcommunity.service.IDecorateService;
import com.smartcommunity.util.ExceptionUtil;
import com.smartcommunity.util.JSONUtil;

import edu.hust.smartcommunity.paginator.domain.PageBounds;
import edu.hust.smartcommunity.paginator.domain.PageList;

public class DecorateServiceImpl implements IDecorateService {

	org.slf4j.Logger logger = LoggerFactory.getLogger(DecorateServiceImpl.class);
	private com.smartcommunity.mapper.DecorateMapper decorateMapper;
	private com.smartcommunity.mapper.DecoratefeeMapper decoratefeeMapper;

	public com.smartcommunity.mapper.DecorateMapper getDecorateMapper() {
		return decorateMapper;
	}

	public void setDecorateMapper(
			com.smartcommunity.mapper.DecorateMapper decorateMapper) {
		this.decorateMapper = decorateMapper;
	}

	public com.smartcommunity.mapper.DecoratefeeMapper getDecoratefeeMapper() {
		return decoratefeeMapper;
	}

	public void setDecoratefeeMapper(
			com.smartcommunity.mapper.DecoratefeeMapper decoratefeeMapper) {
		this.decoratefeeMapper = decoratefeeMapper;
	}

	@Override
	public JSONObject submitDecorate(Decorate decorate) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (decorate == null) {
			return null;
		}
			decorateMapper.insert(decorate);
			jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject submitDecoratefee(Decoratefee decoratefee) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (decoratefee == null) {
			return null;
		}
			decoratefeeMapper.insert(decoratefee);
			jsonObject = JSONUtil.getJsonObject(true);
		return jsonObject;
	}

	@Override
	public JSONObject listDecorate(String roomnumber,Integer pageNo,Integer pageSize) {
		// TODO Auto-generated method stub
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		PageBounds pageBounds = new PageBounds(pageNo,pageSize);
		DecorateExample decorateExample = new DecorateExample();
		if (roomnumber != null ) {
			decorateExample.or().andRoomnumberEqualTo(roomnumber);
		}
		PageList<Decorate> decorates = decorateMapper.selectByExample(decorateExample,pageBounds);
		JSONArray jsonArray = (JSONArray) JSON.toJSON(decorates);
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, decorates.getPaginator().getTotalPages());
		return jsonObject;
	}

	@Override
	public JSONObject listDecoratefee(String roomnumber,Integer pageNo,Integer pageSize) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		PageBounds pageBounds = new PageBounds(pageNo,pageSize);
		DecoratefeeExample decoratefeeExample = new DecoratefeeExample();
		if (roomnumber != null ) {
			decoratefeeExample.or().andRoomnumberEqualTo(roomnumber);
		}
		PageList<Decoratefee> decoratefees = decoratefeeMapper.selectByExample(decoratefeeExample,pageBounds);
		JSONArray jsonArray = (JSONArray) JSON.toJSON(decoratefees);
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, decoratefees.getPaginator().getTotalPages());
		return jsonObject;
	}

}
