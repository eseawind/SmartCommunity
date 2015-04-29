package com.smartcommunity.util;

public class PathUtil {

	public static String getComplaintsPath(String roomnumber) {
		if (roomnumber == null || "".equals(roomnumber)) {
			roomnumber = "anonymous";
		}
		return "../images/" + roomnumber + "/complaints/" ;
	}
	public static String getRepairPath(String roomnumber) {
		if (roomnumber == null || "".equals(roomnumber)) {
			roomnumber = "anonymous";
		}
		return  "../images/" + roomnumber + "/repair/";
	}
}
