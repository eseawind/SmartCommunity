package com.smartcommunity.util;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;



public class InputStreamUtil {

	public static InputStream getInputStream(boolean flag) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", flag);
		try {
			return new java.io.ByteArrayInputStream(jsonObject.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static InputStream getInputStream(JSONObject jsonObject) {
		if (jsonObject == null) {
			jsonObject = JSONUtil.getJsonObject(false);
			JSONUtil.putCause(jsonObject, "没有权限");
		}
		try {
	///		return new java.io.ByteArrayInputStream(jsonObject.toString().getBytes("utf-8"));

			return new java.io.ByteArrayInputStream(JSON.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd HH:mm:ss").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
