package com.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class Test extends com.opensymphony.xwork2.ActionSupport {

	Logger logger = LoggerFactory.getLogger(Test.class);
	private com.smartcommunity.service.ITestService testService;
	private String addByInteceptor;
	private Integer id ;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	java.io.InputStream inputStream;
	public com.smartcommunity.service.ITestService getTestService() {
		return testService;
	}
	public void setTestService(com.smartcommunity.service.ITestService testService) {
		this.testService = testService;
	}
	public java.io.InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(java.io.InputStream inputStream) {
		this.inputStream = inputStream;
	}
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("build " + build + unit + room);
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject("{success:true}");
		com.smartcommunity.pojo.Roomowner testPojo = testService.getTestInfo();
		//jsonObject = JSONObject.fromObject(testPojo);
		throw new java.sql.SQLException();
	//	inputStream = new java.io.ByteArrayInputStream(jsonObject.toString().getBytes());
	//	return SUCCESS;
	}
	public String getAddByInteceptor() {
		return addByInteceptor;
	}
	public void setAddByInteceptor(String addByInteceptor) {
		this.addByInteceptor = addByInteceptor;
	}
	private Integer build ;

	private Integer unit ;
	private Integer room ;
	public Integer getBuild() {
		return build;
	}
	public void setBuild(Integer build) {
		this.build = build;
	}
	public Integer getUnit() {
		return unit;
	}
	public void setUnit(Integer unit) {
		this.unit = unit;
	}
	public Integer getRoom() {
		return room;
	}
	public void setRoom(Integer room) {
		this.room = room;
	}
	
}
