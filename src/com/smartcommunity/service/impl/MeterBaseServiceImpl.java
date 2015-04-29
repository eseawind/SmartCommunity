package com.smartcommunity.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.action.MeterParams;
import com.smartcommunity.mapper.TongYongMapper;
import com.smartcommunity.pojo.Meter;
import com.smartcommunity.pojo.MeterExample;
import com.smartcommunity.service.IBaseService;
import com.smartcommunity.util.JSONUtil;

import edu.hust.smartcommunity.paginator.domain.PageBounds;
import edu.hust.smartcommunity.paginator.domain.PageList;

public class MeterBaseServiceImpl implements IBaseService<com.smartcommunity.action.MeterParams> {

	TongYongMapper tongYongMapper;
	public TongYongMapper getTongYongMapper() {
		return tongYongMapper;
	}

	public void setTongYongMapper(TongYongMapper tongYongMapper) {
		this.tongYongMapper = tongYongMapper;
	}

	@Override
	public JSONObject doQuery(MeterParams params) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		Method method = null;
		try {
			method = tongYongMapper.getClass().getMethod(params.getMethod(), new Class []{com.smartcommunity.pojo.MeterExample.class,PageBounds.class});

		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			jsonObject = com.smartcommunity.util.JSONUtil.getJsonObject(false);
			com.smartcommunity.util.JSONUtil.putCause(jsonObject, e);
			return jsonObject;
		}
			System.out.println(method.getName() + method.getParameterTypes());
		Integer pageSize = params.getPageSize();
		Integer pageNo = params.getPageNo();
		Integer metertype = params.getMetertype();
		String roomnumber = params.getRoomnumber();

			if (pageNo == null) {
				pageNo = 1;
			}
			if (pageSize == null) {
				pageSize = 10;
			}


			PageBounds pageBounds = new PageBounds(pageNo,pageSize);

			MeterExample meterExample = new MeterExample();
			com.smartcommunity.pojo.MeterExample.Criteria criteria = meterExample.or();
			if (metertype != null) {
				criteria.andMetertypeEqualTo(metertype);
			}
			if (roomnumber != null) {
				criteria.andRoomnumberEqualTo(roomnumber);
			}
			meterExample.setOrderByClause("readingtime desc");
			
			PageList<Meter> meters = null;
			try {
				Object[] args = new Object[] {meterExample,pageBounds};
				meters = (PageList<Meter>) method.invoke(tongYongMapper, args);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//PageList<Meter> meters = meterMapper.selectByExample(meterExample,pageBounds);

		//	List<Meter> meters = meterMapper.selectByExample(meterExample);
			JSONArray jsonArray = (JSONArray) com.alibaba.fastjson.JSON.toJSON(meters);
			jsonObject = JSONUtil.getJsonObject(true);
			JSONUtil.putResult(jsonObject, jsonArray);
			JSONUtil.putTotalPage(jsonObject, meters.getPaginator().getTotalPages());
			return jsonObject;
	}

	@Override
	public JSONObject doUpdate(MeterParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject doDelete(MeterParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject doInsert(MeterParams params) {
		// TODO Auto-generated method stub
		return null;
	}

}
