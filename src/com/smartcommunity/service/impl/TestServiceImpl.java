package com.smartcommunity.service.impl;


import com.smartcommunity.mapper.RoomownerMapper;
import com.smartcommunity.pojo.Roomowner;
import com.smartcommunity.pojo.TestPojo;
import com.smartcommunity.service.ITestService;

public class TestServiceImpl implements ITestService {

	private RoomownerMapper testMapper;
	@Override
	public Roomowner getTestInfo() {
		// TODO Auto-generated method stub
		com.smartcommunity.pojo.RoomownerExample example = new com.smartcommunity.pojo.RoomownerExample();
		example.or().andIdEqualTo(1);

		System.out.println("service");
		java.util.List<Roomowner> testPojo = testMapper.selectByExample(example);

		System.out.println("service");
		return testPojo.get(0);
	}
	public RoomownerMapper getTestMapper() {
		return testMapper;
	}
	public void setTestMapper(RoomownerMapper testMapper) {
		this.testMapper = testMapper;
	}

}
