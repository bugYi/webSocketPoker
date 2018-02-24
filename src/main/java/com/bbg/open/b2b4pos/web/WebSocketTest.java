package com.bbg.open.b2b4pos.web;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;

import com.bbg.open.b2b4pos.entity.LoadBillDetail;
import com.bbg.open.b2b4pos.service.LoadBillDetailService;
import com.bbg.open.b2b4pos.web.param.SaleOrderParam;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocketTest/{roomCode}/{userId}")
public class WebSocketTest {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	private static ConcurrentHashMap<String, Integer> roomCount = new ConcurrentHashMap<String, Integer>();
	
	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	//private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();
	private static ConcurrentHashMap<String, WebSocketTest> webSocketSet = new ConcurrentHashMap<String, WebSocketTest>();
	
	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException, InterruptedException {

		// Print the client message for testing purposes
		System.out.println("来自客户端的消息:" + message);
		if("@heart".equals(message)){
			return;
		}
		// 发送消息
		//session.getBasicRemote().sendText("发送接收到的信息：" + message);
		//群发消息
        /*for(WebSocketTest item: webSocketSet){
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }*/
		String userId = session.getPathParameters().get("userId");
		String roomCode = session.getPathParameters().get("roomCode");
		
		Enumeration<String> enu = webSocketSet.keys();
		while (enu.hasMoreElements()) {//遍历所有连接
			String key = (String) enu.nextElement();
			if(key.split("-")[0].equals(roomCode)){//根据当前发消息人的房间号，发送对应的消息到对应的房间中
				webSocketSet.get(key).sendMessage(userId+":"+message+"(当前房间有"+ roomCount.get(roomCode) +"人),在线"+ getOnlineCount() +"人");
			}			
		}
		

	}

	@OnOpen
	public void onOpen(Session session,@PathParam("userId")String  userId,@PathParam("roomCode")String  roomCode) {
		 /*获取从/websocket开始的整条链接，用于获取userId？***=***的参数*/
        //String uri = session.getRequestURI().toString();
		this.session = session;
		//webSocketSet.add(this);     //加入set中
		webSocketSet.put(roomCode+"-"+userId, this);//房间号和用户名做key
		addRoomCount(roomCode);//增加房间人数
		addOnlineCount();           //在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	@OnClose
	public void onClose() {
		webSocketSet.remove(this);  //从set中删除
		subRoomCount(this.session.getPathParameters().get("roomCode"));//房间人数-1
		subOnlineCount();           //在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}
	
	
	
	/**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
        if(WebSocketTest.onlineCount == 0 ){
        	webSocketSet.clear();
        	roomCount.clear();
        }
    }
    
    /**
     * 根据roomCode增加房间人数
     * @param roomCode
     */
    public static synchronized void addRoomCount(String roomCode) {
    	//查询房间是否有人，累计房间人数
		if(!roomCount.containsKey(roomCode)){
			roomCount.put(roomCode, 1);
		}else{
			roomCount.put(roomCode, roomCount.get(roomCode).intValue()+1);
		}
    }
    
    /**
     * 根据roomCode增加房间人数
     * @param roomCode
     */
    public static synchronized void subRoomCount(String roomCode) {
    	//查询房间是否有人，累计房间人数
		if(!roomCount.containsKey(roomCode)){
			roomCount.put(roomCode, 0);
		}else{
			roomCount.put(roomCode, roomCount.get(roomCode).intValue()-1);
		}
    }
}