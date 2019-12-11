package com.citycloud.ccuap.intellisense.utils;


import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by 759517209@qq.com on 2017/5/9.
 * 这个类即实现了进行数据库操作的Servlet类，又实现了Websocket的功能.
 */
//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket，类似Servlet的注解mapping；
// servlet的注册放在了web.xml中。
@ServerEndpoint(value = "/websocket")
@Slf4j
@Component
public class ManagerServlet  {
	
	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static CopyOnWriteArraySet<ManagerServlet> webSocketSet = new CopyOnWriteArraySet<ManagerServlet>();
    //这个session不是Httpsession，相当于用户的唯一标识，用它进行与指定用户通讯
    private  javax.websocket.Session session=null;

    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was
     * successful.
     * 建立websocket连接时调用
     */
    @OnOpen
    public void onOpen(Session session){
    	if(null == session){
    		log.error("[HYMAN]Session is null" + session);
    		return;
    	}
        log.warn("[HYMAN]Session " + session.getId() + " has opened a connection");
        try {
            this.session=session;
            webSocketSet.add(this);     //加入set中
            log.warn("[HYMAN]session webSocketSet" + webSocketSet.size());
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     * 接收到客户端消息时使用，这个例子里没用
     */
    @OnMessage
    public void onMessage(String message, Session session){
    	if(null == session){
    		return;
    	}
    	log.warn("[HYMAN] receive client:"+session.getId()+" ;message" + message);
    }

    /**
     * The user closes the connection.
     *
     * Note: you can't send messages to the client from this method
     * 关闭连接时调用
     */
    @OnClose
    public void onClose(Session session){
    	if(null == session)return;
        webSocketSet.remove(this);  //从set中删除
        log.warn("[HYMAN]Session " +session.getId()+" has closed!");
    }

    /**
     * 注意: OnError() 只能出现一次.   其中的参数都是可选的。
     * @param session
     * @param t
     */
    @OnError
    public void onError(Session session, Throwable t) {
    	log.error("[HYMAN]Session error " + t.getMessage());
        t.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @throws IOException
     * 发送自定义信号，“1”表示告诉前台，数据库发生改变了，需要刷新
     */
    public static void sendMessage(){
    	log.warn("[HYMAN]datd is change and  sendMessage ");
    	log.warn("[HYMAN]need to send client num is " + webSocketSet.size());
        //群发消息
        for(ManagerServlet item: webSocketSet){
            try {
                item.session.getBasicRemote().sendText("refresh");
            } catch (IOException e) {
            	log.error("[HYMAN]sendMessage error!!!!"+e.getMessage());
                e.printStackTrace();
                continue;
            }
        }
        log.warn("[HYMAN]sendMessage finished!!!!");
    }
    
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @throws IOException
     * 发送自定义信号，“1”表示告诉前台，数据库发生改变了，需要刷新
     */
    public static void broadcastMessage(){
    	log.warn("[HYMAN]datd is change and  sendMessage ");
    	log.warn("[HYMAN]need to send client num is " + webSocketSet.size());
    	
    	webSocketSet.forEach((v) -> {
    		try {
                v.session.getBasicRemote().sendText("refresh");
            } catch (IOException e) {
            	log.error("[HYMAN]sendMessage error!!!!"+e.getMessage());
                e.printStackTrace();
            }
    	});
        log.warn("[HYMAN]sendMessage finished!!!!");
    }
    
}
