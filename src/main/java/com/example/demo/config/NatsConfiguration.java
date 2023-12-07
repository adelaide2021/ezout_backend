package com.example.demo.config;

import com.example.demo.receive.MessageListener;
import io.nats.client.Connection;
import io.nats.client.ConnectionListener;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class NatsConfiguration {
//    @Autowired
//    private WebSocketSession webSocketSession;
    private WebSocketSession webSocketSession;


//    @Bean
//    public JetStream jetStream(Connection natsConnection) throws IOException {
//        return natsConnection.jetStream();
//    }
//
//    @Bean
//    public Connection natsConnection() {
//        try {
//            Connection natsConnection = Nats.connect("nats://39.97.255.141:4222");
//            createStream(natsConnection);
//            return natsConnection;
//        } catch (Exception e) {
//            throw new RuntimeException("Error creating NATS connection", e);
//        }
//    }
//
//    private void createStream(Connection nc) {
//        try {
//            JetStreamManagement jetStreamManagement = nc.jetStreamManagement();
//            StreamConfiguration sc = new StreamConfiguration.Builder()
//                    .name("demo")
//                    .subjects("demo.*")
//                    .replicas(1)
//                    .retentionPolicy(RetentionPolicy.Limits)
//                    .discardPolicy(DiscardPolicy.Old)
//                    .noAck(true)
//                    .build();
//            jetStreamManagement.addStream(sc);
//        } catch (Exception e) {
//            throw new RuntimeException("Error creating JetStream stream", e);
//        }
//    }

    @Bean(name = "natsConnection")
    public Connection natsConnection(NatsProperties properties) throws IOException, InterruptedException {
        String[] str = properties.getNatsUrls().split(",");
        Options.Builder builder = new Options.Builder()
                // 配置 nats 服务器地址
                .servers(str)
                // nats 监听
                .connectionListener(new MessageListener())
                // 最大重连次数
                .maxReconnects(properties.getMaxReconnect())
                // 重连等待时间
                .reconnectWait(Duration.ofSeconds(properties.getReconnectWait()))
                // 连接超时时间
                .connectionTimeout(Duration.ofSeconds(properties.getConnectionTimeout()));
        if (properties.getToken() != null) {
            builder.token(properties.getToken().toCharArray());
        }
        //连接 nats
        return Nats.connect(builder.build());

    }

}
