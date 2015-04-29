package com.smartcommunity.websocket;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebSocketMessageInboundPool {
	
	private static final Map<String,WebSocketMessageInbound > connections = 
			new HashMap<String,WebSocketMessageInbound>();  
	
	//向连接池中添加连接  
    public static void addMessageInbound(WebSocketMessageInbound inbound){  
        // 会话 id 与连接对应 
        connections.put(inbound.getSessionID(), inbound);  
    }  
    
    public static void removeMessageInbound(WebSocketMessageInbound inbound){  
        //移除连接  
        System.out.println("session : " + inbound.getSessionID() + " exit..");  
        connections.remove(inbound.getSessionID());  
    }  
    
    public static void sendMessageToUser(String sessionID,String message){  
        try {  
            //向特定的用户发送数据  
            System.out.println("send message to session : " + sessionID + " ,message content : " + message);  
            WebSocketMessageInbound inbound = connections.get(sessionID);  
            if(inbound != null){  
                inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    //向所有的用户发送消息  
    public static void sendMessage(String message){  
        try {  
            Set<String> keySet = connections.keySet();  
            for (String key : keySet) {  
                WebSocketMessageInbound inbound = connections.get(key);  
                if(inbound != null){  
                    System.out.println("send message to user : " + key + " ,message content : " + message);  
                    inbound.getWsOutbound().writeTextMessage(CharBuffer.wrap(message));  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  

}
