//package com.example.demo.web;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.BinaryMessage;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.AbstractWebSocketHandler;
//
//@Component
//public class MyWebSocketHandler extends AbstractWebSocketHandler {
//
//
//    private WebSocketSession session;
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        this.session = session;
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        session.sendMessage(message);
//    }
//
//    @Override
//    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
//        session.sendMessage(message);
//    }
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
//        this.session = null;
//        exception.printStackTrace();
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        this.session = null;
//    }
//}
//
//
