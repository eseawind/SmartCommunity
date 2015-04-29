package com.smartcommunity.util;

import java.util.Date;

public class DateUtil {

	public static String dateToString(Date date) {

		if (date == null) {
			return "";
		}
		String dateString = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return dateString;
	}
}
