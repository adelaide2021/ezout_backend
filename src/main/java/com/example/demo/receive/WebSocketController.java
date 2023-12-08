package com.example.demo.receive;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

//@Controller
//public class WebSocketController {
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @MessageMapping("/updateShopId")
//    public void updateShopId(@Payload String newShopId) {
//        // 在从前端接收到新值时更新当前 shop_id
//        // 这里可以添加其他逻辑
//
//        // 发送更新后的值到前端
//        messagingTemplate.convertAndSend("/topic/shopId", newShopId);
//    }
//
//}
