package com.example.demo.send;


import io.nats.client.Connection;
import jakarta.annotation.Resource;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("api/sendmessage")
public class MessageSendController {
//    private final JetStream js;
//
//    @Autowired
//    public MessageSendController(JetStream js) {
//        this.js = js;
//    }
@Resource(name = "natsConnection")
private Connection natsConnection;
    @PostMapping
    public void submitMessage(@RequestBody Map<String, String> formData) throws InterruptedException {

        // System.out.println(formData.toString());
        try {
            natsConnection.publish("demo", formData.toString().getBytes());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
