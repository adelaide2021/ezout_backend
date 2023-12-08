package com.example.demo.send;


import com.example.demo.data.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.nats.client.Connection;
import jakarta.annotation.Resource;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/sendmessage")
public class MessageSendController {

    @Resource(name = "natsConnection")
    private Connection natsConnection;

    @PostMapping
    public void submitMessage(@RequestBody List<Message> formData) throws InterruptedException {
        try {
            String shopID = formData.get(0).getShop_id();
            System.out.println("fomr send: " + shopID);
            String jsonData = new ObjectMapper().writeValueAsString(formData);
            String topic = "demo." + shopID;
            natsConnection.publish(topic, jsonData.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
