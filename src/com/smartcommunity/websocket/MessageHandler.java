package com.smartcommunity.websocket;

import net.sf.json.JSONObject;

public class MessageHandler {
	
	public String process(String message) {
//		JSONObject request = JSONObject.fromObject(message);
//		System.out.println(request);
//		Integer id = request.getInt("id");
//		boolean isFromApp = request.getBoolean("isFromApp");
//		NavigateService navigateService = SystemInit.getApplicationContext().getBean("navigateService",NavigateService.class);
//		JSONObject response = navigateService.getPageById(id, isFromApp);
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("websocket", "MessageHandler");
		return message;
	}

}
