package com.example.demo.config;

import com.example.demo.receive.MessageListener;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.Duration;

@Configuration
public class NatsConfiguration {

    // WebSocket session for communication
    private WebSocketSession webSocketSession;

    @Bean(name = "natsConnection")
    public Connection natsConnection(NatsProperties properties) throws IOException, InterruptedException {
        String[] str = properties.getNatsUrls().split(",");
        Options.Builder builder = new Options.Builder()
                // Configuring NATS server addresses
                .servers(str)
                // Adding a connection listener for handling connection events
                .connectionListener(new MessageListener())
                .maxReconnects(properties.getMaxReconnect())
                .reconnectWait(Duration.ofSeconds(properties.getReconnectWait()))
                .connectionTimeout(Duration.ofSeconds(properties.getConnectionTimeout()));
        if (properties.getToken() != null) {
            builder.token(properties.getToken().toCharArray());
        }
        // Connecting to NATS with the configured options
        return Nats.connect(builder.build());
    }
}
