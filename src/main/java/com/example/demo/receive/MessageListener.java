//package com.example.demo.receive;
//
//import io.nats.client.Connection;
//import io.nats.client.ConnectionListener;
//import io.nats.client.Dispatcher;
//
//public class MessageListener  implements ConnectionListener {
//
//    @Override
//    public void connectionEvent(Connection conn, Events type) {
//        if(Connection.Status.CONNECTED.equals(conn.getStatus())){
//
//            // 连接成功后进行消息订阅
//            subscribe(conn);
//        }
//
//    }
//    public void subscribe(Connection conn){
//
//        Dispatcher dispatcher = conn.createDispatcher(msg -> {
//       System.out.println(new String(msg.getData()));
//        });
//
//        //订阅 subject 为 demo 的消息
//        dispatcher.subscribe("demo");
//
//        // 不关闭connection，方便测试多次接收消息
//    }
//
//}
package com.example.demo.receive;

import com.example.demo.data.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import io.nats.client.Dispatcher;
import org.springframework.web.socket.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MessageListener implements ConnectionListener, WebSocketHandler {

    private static final ConcurrentLinkedDeque<WebSocketSession> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();



    @Override
    public void connectionEvent(Connection conn, Events type) {
        if (Connection.Status.CONNECTED.equals(conn.getStatus())) {
            // After connecting, subscribe to messages
            subscribe(conn);
        }
    }

    public void subscribe(Connection conn) {
        Dispatcher dispatcher = conn.createDispatcher(msg -> {
            // Get real data
            byte[] realData = msg.getData();
            // System.out.println(realData.toString());

            // Send real data to the WebSocket
            try {
                sendToWebSocket(realData);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        // Subscribe to messages with the subject "demo"
        dispatcher.subscribe("demo");

        // Do not close the connection to test receiving messages multiple times
    }

    private void sendToWebSocket(byte[] data) throws JsonProcessingException {
        String realDataAsString = new String(data, StandardCharsets.UTF_8);
        System.out.println("from receive, " + realDataAsString);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> myObjects = objectMapper.readValue(realDataAsString, new TypeReference<List<Message>>() {});

        concurrentLinkedDeque.forEach(item -> {
            try {
                if (item.isOpen()) {
                    item.sendMessage(new TextMessage(objectMapper.writeValueAsString(myObjects)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        concurrentLinkedDeque.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        concurrentLinkedDeque.forEach(item -> {
            try {
                if (item.isOpen()) {
                    item.sendMessage(message);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}


