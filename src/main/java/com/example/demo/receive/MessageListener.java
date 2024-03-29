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
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MessageListener implements ConnectionListener, WebSocketHandler {

    private static final ConcurrentLinkedDeque<WebSocketSession> concurrentLinkedDeque = new ConcurrentLinkedDeque<>();
    private static String currentShopId = "";

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

            // Send real data to the WebSocket
            try {
                sendToWebSocket(realData);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        // Subscribe to messages with the subject "demo"
        dispatcher.subscribe("demo.*");
    }

    private void sendToWebSocket(byte[] data) throws JsonProcessingException {
        String realDataAsString = new String(data, StandardCharsets.UTF_8);
        System.out.println("receiving data on backend: " + realDataAsString);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Message> myObjects = objectMapper.readValue(realDataAsString, new TypeReference<List<Message>>() {});
        List<Message> filteredMessages = myObjects.stream()
                .filter(message -> currentShopId.equals(message.getShop_id()))
                .collect(Collectors.toList());
        System.out.println("shop id when sending message:  " + currentShopId);
        concurrentLinkedDeque.forEach(item -> {
            try {
                if (item.isOpen()) {
                    item.sendMessage(new TextMessage(objectMapper.writeValueAsString(filteredMessages)));
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
        String payload = (String) message.getPayload();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> messageMap = objectMapper.readValue(payload, new TypeReference<Map<String, String>>() {});

            if (messageMap.containsKey("action") && messageMap.get("action").equals("updateShopId")) {
                String newShopId = messageMap.get("shopId");
                if (newShopId != null && !newShopId.isEmpty()) {
                    //currentShopId.set(newShopId);
                    currentShopId = newShopId;
                    System.out.println("update currentShopId to be：" + currentShopId);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


