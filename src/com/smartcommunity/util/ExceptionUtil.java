package com.smartcommunity.util;

import java.util.Arrays;

public class ExceptionUtil {

	public static String getStackTrack(Exception e) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(e + "\n");
		stringBuilder.append(Arrays.toString(e.getStackTrace()) );
		return stringBuilder.toString();
	}
}
