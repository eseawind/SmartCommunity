package com.smartcommunity.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.LoggerFactory;
















import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.smartcommunity.action.MeterParams;
import com.smartcommunity.mapper.AccountMapper;
import com.smartcommunity.mapper.AccountRecordMapper;
import com.smartcommunity.mapper.MeterMapper;
import com.smartcommunity.pojo.Account;
import com.smartcommunity.pojo.AccountExample;
import com.smartcommunity.pojo.AccountRecord;
import com.smartcommunity.pojo.Meter;
import com.smartcommunity.pojo.MeterExample;
import com.smartcommunity.service.IBaseService;
import com.smartcommunity.service.IMeterService;
import com.smartcommunity.util.ConstantPool;
import com.smartcommunity.util.DateUtil;
import com.smartcommunity.util.ExceptionUtil;
import com.smartcommunity.util.JSONUtil;

import edu.hust.smartcommunity.paginator.domain.PageBounds;
import edu.hust.smartcommunity.paginator.domain.PageList;

public class MeterServiceImpl implements IMeterService<MeterParams> {

	org.slf4j.Logger logger = LoggerFactory.getLogger(MeterServiceImpl.class);
	private MeterMapper meterMapper;
	private AccountRecordMapper accountRecordMapper;
	private AccountMapper accountMapper;

	public MeterMapper getMeterMapper() {
		return meterMapper;
	}

	public void setMeterMapper(MeterMapper meterMapper) {
		this.meterMapper = meterMapper;
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject submitMeterInfo(java.util.List<Meter> meters) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (meters == null || meters.size() < 1) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "抄表信息为空");
			return jsonObject;
		}
		for (Meter meter : meters) {

			if (meter.getRoomnumber() == null) {
				jsonObject = com.smartcommunity.util.JSONUtil.getJsonObject(false);
				JSONUtil.putCause(jsonObject, "房间号不能为空");
				return jsonObject;
			}
		}
		
		for (Meter meter : meters) {
				meterMapper.insertSelective(meter);
		}
		Set<Integer> idSet = new HashSet<Integer>();

		for (Meter meter : meters) {
				idSet.add(meter.getId());
		}
		jsonObject = JSONUtil.getJsonObject(true);
		if (idSet.size() > 0) {

			MeterExample meterExample = new MeterExample();
			meterExample.or().andIdIn(new ArrayList<Integer>(idSet));
			meters = meterMapper.selectByExample(meterExample);
			for (Meter meter : meters) {
				if (meter.getMetertype() == ConstantPool.TYPE_WATER_METER) {
					// 水表 数据
					jsonObject.put(ConstantPool.FIELD_WATER_CONSUME, meter.getConsumedata());
					jsonObject.put(ConstantPool.FIELD_WATER_UNIT_PRICE, meter.getUniteprice());
					jsonObject.put(ConstantPool.FIELD_WATER_TOTAL_PRICE, meter.getTotalprice());
				} else if (meter.getMetertype() == ConstantPool.TYPE_ELEC_METER) {
					// 电表数据
					jsonObject.put(ConstantPool.FIELD_ELEC_CONSUME, meter.getConsumedata());
					jsonObject.put(ConstantPool.FIELD_ELEC_UNIT_PRICE, meter.getUniteprice());
					jsonObject.put(ConstantPool.FIELD_ELEC_TOTAL_PRICE, meter.getTotalprice());
			
				}
			}
		}
		
		return jsonObject;
	}


	//@Override
	public JSONObject listMeterInfo(Integer metertype, String roomnumber,
			Integer pageNo, Integer pageSize) {
		// TODO Auto-generated method stub
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
			criteria.andRoomnumberLike(roomnumber);
		}
		meterExample.setOrderByClause("readingtime desc");
		
		PageList<Meter> meters = meterMapper.selectByExample(meterExample,pageBounds);

	//	List<Meter> meters = meterMapper.selectByExample(meterExample);
		JSONArray jsonArray = (JSONArray) com.alibaba.fastjson.JSON.toJSON(meters);
		JSONObject jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
		JSONUtil.putTotalPage(jsonObject, meters.getPaginator().getTotalPages());
		return jsonObject;
	}

	@org.springframework.transaction.annotation.Transactional
	@Override
	public JSONObject paying(Integer id, Float amount) {
		// TODO Auto-generated method stub
		if (id == null || amount == null) {
			return null;
		}
		JSONObject jsonObject = null;
		Meter meter = meterMapper.selectByPrimaryKey(id);
		if (meter == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有此账单");
			return jsonObject;
		}
		if (amount < meter.getTotalprice()) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "金额不足");
			return jsonObject;
		}
		if (meter.getState() == true) {

			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "已经交过");
			return jsonObject;
		}
		meter.setState(true); // 设置为已经缴费
		meterMapper.updateByPrimaryKeySelective(meter);
		// 保存缴费记录
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setMeterid(meter.getId());
		accountRecord.setRoomnumber(meter.getRoomnumber());
		accountRecord.setDate(new java.util.Date());
		accountRecord.setShouldpay(meter.getTotalprice());
		accountRecord.setActualpay(amount);
		accountRecord.setType(0);
		// 插入缴费记录
		accountRecordMapper.insertSelective(accountRecord);
		
		Account account = null;
		AccountExample accountExample = new AccountExample();
		accountExample.or().andRoomnumberEqualTo(meter.getRoomnumber());
		List<Account> accounts = accountMapper.selectByExample(accountExample);

		if (accounts.size() <= 0) {
			account = new Account();
			account.setRemain((float) 0.0);
			account.setRoomnumber(meter.getRoomnumber());
			accountMapper.insertSelective(account);
		} else {
			account = accounts.get(0); // 取得账户
		}

		if (amount > meter.getTotalprice()) {
			// 如果没有账户就创建一个
		account.setRemain(account.getRemain() + amount - meter.getTotalprice());
		accountMapper.updateByPrimaryKeySelective(account);
		
		} 
		jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putAccountRemain(jsonObject, account.getRemain());
		return jsonObject;
	}

	public AccountRecordMapper getAccountRecordMapper() {
		return accountRecordMapper;
	}

	public void setAccountRecordMapper(AccountRecordMapper accountRecordMapper) {
		this.accountRecordMapper = accountRecordMapper;
	}

	public AccountMapper getAccountMapper() {
		return accountMapper;
	}

	public void setAccountMapper(AccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}


	@Override
	public JSONObject list(MeterParams meterParams) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		try {
			jsonObject = listMeterInfo(meterParams.getMetertype(), meterParams.getRoomnumber(), 
					meterParams.getPageNo(), meterParams.getPageSize());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(com.smartcommunity.util.ExceptionUtil.getStackTrack(e));
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, e);
		}
		return jsonObject;
	}

	@Override
	public JSONObject getAccount(String roomNumber) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = null;
		if (roomNumber == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "房间号不正确");
			return jsonObject;
		}
		AccountExample accountExample = new AccountExample();
		accountExample.or().andRoomnumberLike(roomNumber);
		List<Account> accounts = accountMapper.selectByExample(accountExample);
		if (accounts.size() <= 0) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有找到帐户");
			return jsonObject;
		}
		JSONArray jsonArray = (JSONArray) com.alibaba.fastjson.JSON.toJSON(accounts);
	//	Account account = accounts.get(0);
		jsonObject = JSONUtil.getJsonObject(true);
		JSONUtil.putResult(jsonObject, jsonArray);
	//	JSONUtil.putAccountRemain(jsonObject, account.getRemain());
		return jsonObject;
	}

}
