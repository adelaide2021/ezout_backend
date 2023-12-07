package com.example.demo.web;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

public class ApiController {
    private final SimpMessagingTemplate messagingTemplate;

    public ApiController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/api/receive-message")
    public void receiveMessage(@RequestBody String message) {
        // 将接收到的消息发送到前端
        messagingTemplate.convertAndSend("demo", message);
    }
}
