package com.jsxztshaohaibo.websocket;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/ws/WebSocketTest/{reqMsg}")
public class WebSocketTest {
	private  String msg;
	//连接打开时执行
    @OnOpen
    public void onOpen(@PathParam("reqMsg") String reqMsg, Session session) {
    	msg = reqMsg;
    	
        System.out.println("Connected ... " + session.getId());
        try {
			session.getBasicRemote().sendText(">>>>> 服务端接收到你发来的连接请求，并已建立连接>>>>>>");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    }

    //收到消息时执行
    @OnMessage
    public String onMessage(String message, Session session) {
        System.out.println(msg + "：" + message);
        String[] split = message.split("\\@#@");
       /* try {
			session.getBasicRemote().sendText(">>>>> 服务端接收到你发来的消息>>>>>>"+ split[1]);
		} catch (IOException e) {
			
			e.printStackTrace();
		}*/
        
        return ">>>>> 服务端接收到你发来的消息>>>>>>"+msg + "：" + split[1];
    }

    //连接关闭时执行
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

    //连接错误时执行
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
   public static void main(String[] args) {
	   String message = "adsfadfads >>> @#@adfasdfasdfasd";
	   String[] split = message.split("@#@");
	   System.out.println(split.length);
   }
}
