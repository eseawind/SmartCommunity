package com.smartcommunity.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;

public class WebSocketMessageInbound extends MessageInbound {
	
	//当前连接的用户会话 id
    private final String sessionID; 
    
    private MessageHandler messageHandler = new MessageHandler();
  
    public WebSocketMessageInbound(String sessionID) {  
    	System.out.println("websocket");
        this.sessionID = sessionID;  
    }  
  
    public String getSessionID() {  
        return this.sessionID;  
    }  

    //建立连接的触发的事件  
    @Override  
    protected void onOpen(WsOutbound outbound) {  

        // 向连接池添加当前的连接对象  
        WebSocketMessageInboundPool.addMessageInbound(this);  
        //向当前连接发送当前报警信息
//        List<String> infos = AlarmInfoCache.getAlarmInfoCache().getInfos();
//        infos.addAll(FireInfoCache.getFireInfoCache().getInfos());
//        for(String info : infos) {
//        	System.out.println(info);
//        }
//        if(!infos.isEmpty()) {
//        	// add
//        	System.out.println("send infos to web browser");
//        	// addend
//        	// del
//        	//WebSocketMessageInboundPool.sendMessageToUser(this.sessionID, JSONArray.fromObject(infos).toString());  
//        	// delend
//        }
 //   	WebSocketMessageInboundPool.sendMessageToUser(this.sessionID, "下午停水"); 
    }  

    // 触发关闭事件，在连接池中移除连接  
    @Override  
    protected void onClose(int status) {  
        WebSocketMessageInboundPool.removeMessageInbound(this);  
    }

	@Override
	protected void onBinaryMessage(ByteBuffer message) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onTextMessage(CharBuffer message) throws IOException {
		// TODO Auto-generated method stub
		String response = messageHandler.process(message.toString());
		WebSocketMessageInboundPool.sendMessageToUser(sessionID, response);
	}  
  
    

}
