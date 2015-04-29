package com.smartcommunity.websocket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

@WebServlet(urlPatterns = { "/message"})  
public class WebSocketMessageServlet extends WebSocketServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return new WebSocketMessageInbound(request.getSession().getId()); 
	}

}
