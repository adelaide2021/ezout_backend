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
            System.out.println("from send, " + formData.toString());
            // Convert List<Message> to JSON string
            String jsonData = new ObjectMapper().writeValueAsString(formData);
            natsConnection.publish("demo", jsonData.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
