package com.smartcommunity.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UTIL {
	/** truts2 返回结果 */

	public static final String ANDROID_VERSION_PATH = "./apk/update.xml";
	public static final String ANDROID_DOWNLOAD_PATH = "./apk/latest.apk";
	public static final String ANDROID_VERSION_ROOT = "update";
	public static final String ANDROID_VERSION_VERSION = "version";
	public static final String ANDROID_VERSION_VALUE = "value";
	
	public static final String RESULT_TOLOGIN = "tologin";
	public static final String RESULT_PERMISION = "permision";
	
	public static final Integer roleother =0;
	public static final Integer roleuser =2;
	public static final Integer rolemanager = 1;
	public static final String sessionUser = "user";
	public static final String sessionTelephone = "telephone";
	public static final String sessionRoles = "roles";
	public static final String sessionResources = "resources";
	public static final String sessionRoomNumber = "roomnumber";
	

	public static final String ROOM_SPLIT = "-";
	public static final String ROOM_BUILDNO = "buildNo";
	public static final String ROOM_UNITNO = "unitNo";
	public static final String ROOM_ROOMNO = "roomNo";
	

	public static final String COOKIE_NAME = "name";
	public static final String COOKIE_PASSWORD = "password";
	public static final String COOKIE_JSESSIONID = "JSESSIONID";
	
	public static final String roomnumberPattern = "^[0-9]{1,3}-[0-9]{1,3}-[0-9]{3}$";
	public static final String identityPattern = "^[0-9]{17}[0-9xX]$";

	public static final String defaultPassowrd = "123456";

	public static final String BUILDING_NAME = "name";
	public static final String BUILDING_NUMBER = "number";
	
	
	public static String byteToHexString(byte[] bytes) {

	    StringBuilder stringBuilder = new StringBuilder();  
	    for (int i = 1; i < bytes.length; i++) {   
	    	stringBuilder.append(Integer.toHexString(bytes[i] & 0xff));
	    }  
	    return stringBuilder.toString().toLowerCase();
	}
	public static String MD5Encrypt(String string) {

		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
			byte [] digest = md5.digest(string.getBytes());

			return byteToHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return string;
		}
	}
	/**
	 * 检查房间号是否正确
	 * @param string
	 * @return
	 */
	public static boolean checkRoomnumber(String roomNo) {

		if (roomNo == null) 
			return false;

		return roomNo.matches(roomnumberPattern);
	}
	/**
	 * 检查身份证号是否正确
	 * @param identity
	 * @return
	 */
	public static boolean checkIdentity(String identity) {

		String IdentityPattern = "^[0-9]{17}[x|0-9]$";
		if (identity == null) 
			return false;

		return identity.matches(IdentityPattern);
	}
	/**
	 * 根据参数构造房间的查询条件
	 * @param build
	 * @param unit
	 * @param room
	 * @return
	 */
	public static String getRoomnumberSearchPattern(Integer build,Integer unit,Integer room) {

		StringBuilder stringBuilder = new StringBuilder();
		if (build == null || build == 0) {
			stringBuilder.append("%");
		} else {
			stringBuilder.append(build);
		}
		stringBuilder.append(ROOM_SPLIT);

		if (unit == null || unit == 0) {
			stringBuilder.append("%");
		} else {
			stringBuilder.append(unit);
		}
		stringBuilder.append(ROOM_SPLIT);

		if (room == null || room == 0) {
			stringBuilder.append("%");
		} else {
			stringBuilder.append(String.format("%03d", room));
		}
		return stringBuilder.toString();

	}
	/**
	 * 组合成用户完整房间号
	 * @param build
	 * @param unit
	 * @param room
	 * @return
	 */
	public static String getRoomnumber(Integer build,Integer unit,Integer room) {

		StringBuilder stringBuilder = new StringBuilder();
		if (build == null || build == 0 || unit == null || unit == 0 || room == null || room == 0) {
			return null;
		}
		stringBuilder.append(build + ROOM_SPLIT + unit + ROOM_SPLIT + String.format("%03d", room));

		return stringBuilder.toString();
	}
	
	public static String splitRoomNumber(String roomnumber) {

		if (roomnumber == null) {
			return null;
		}
//		if (!UTIL.checkRoomnumber(roomnumber)) {
//			return "未知房间号";
//		}
		String [] room = roomnumber.split("-");
		StringBuilder stringBuilder = new StringBuilder();
		if (! "0".equals(room[0] )) {
			stringBuilder.append(room[0] + "栋");
			if (!"0".equals(room[1])) {
				stringBuilder.append(room[1] + "单元");
				if (!"000".equals(room[2])) {
					stringBuilder.append(room[2] + "房间");
				}
			}
		} else {

			stringBuilder.append("所有楼栋");
		}

		return stringBuilder.toString();
	}
	
	public static String getNoticeRoomNumber(Integer buildNo,Integer unitNo,Integer roomNo) {
		// 发布的类型
		StringBuilder stringBuilder = new StringBuilder();
		if (buildNo == null || buildNo == 0) {
			stringBuilder.append("0");	// 所有楼栋
		} else {
			stringBuilder.append(buildNo);
			if (unitNo == null || unitNo == 0) {
				stringBuilder.append("-" + 0); // 所有单元
			} else {
				stringBuilder.append("-" + unitNo);
				if (roomNo == null || roomNo == 0) {
					stringBuilder.append("-" + "0");	// 所有房间
				} else {
					if (roomNo > 1000) {
						return null;
					}
					stringBuilder.append("-" + String.format("%03d", roomNo));
				}
			}
			
		}

		return stringBuilder.toString();
	}
	public static String getCountSql(String sql) {
			Pattern pat = Pattern.compile(ConstantPool.PAGE_SEARCH_PATTERN,Pattern.CASE_INSENSITIVE);  
			int i = sql.indexOf("from");
			if (i != -1 ) {
				sql = "select count(1) " + sql.substring(i);
			}
		   Matcher matcher = pat.matcher(sql);     
		   if (matcher.find()) { 
		     String temp = sql.substring(matcher.start(),matcher.end());
		     sql = sql.replaceAll(temp, ConstantPool.PAGE_REPLACE_PATTERN);
		   }     
		   return sql;
	}
}
