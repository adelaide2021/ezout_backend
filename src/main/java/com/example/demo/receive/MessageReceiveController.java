package com.example.demo.receive;//package com.example.demo.receive;
//
//import io.nats.client.*;
//
//import java.nio.charset.StandardCharsets;
//import java.time.Duration;
//import java.util.Iterator;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import io.nats.client.Message;
//import io.nats.client.api.AckPolicy;
//import io.nats.client.api.ConsumerConfiguration;
//import io.nats.client.api.DeliverPolicy;
//import io.nats.client.api.ReplayPolicy;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MessageReceiveController {
//
////    private final ConsumerConfiguration c = ConsumerConfiguration.builder()
////                    .durable("demo")
////                    .deliverPolicy(DeliverPolicy.All)
////                    .startSequence(-1)
////                    .deliverSubject("demo")
////                    .ackPolicy(AckPolicy.Explicit)
////                    .ackWait(1000)
////                    .maxAckPending(-1)
////                    .replayPolicy(ReplayPolicy.Instant)
////                    .maxDeliver(-1)
////                    .filterSubject("demo")
////                    .build(); //xinijiain
//
//    private final JetStream js;
//    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//
//   @Autowired
//    public MessageReceiveController(JetStream js) {
//        this.js = js;
//    }
//
//    @PostConstruct
//    public void initializeSubscription() throws Exception {
//        PullSubscribeOptions pullSubscribeOptions = PullSubscribeOptions.builder()
//                .durable("demo").stream("demo")
//                .build();
//
//        JetStreamSubscription sub = js.subscribe("demo", pullSubscribeOptions);
//
//        while (true) {
//                try {
//                    Message message = sub.nextMessage(Duration.ofSeconds(5));
//                    if (message != null) {
//                        processMessage(message);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
////        executorService.submit(() -> {
////            while (true) {
////                try {
////                    Message message = sub.nextMessage(Duration.ofSeconds(5));
////                    if (message != null) {
////                        processMessage(message);
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        });
//      //  System.out.println("Subscription initialized.");
//    }
//
//    private void processMessage(Message message) {
//        String messageData = new String(message.getData(), StandardCharsets.UTF_8);
//        System.out.println("receive message" + messageData);
//    }
//}
//
//
//
