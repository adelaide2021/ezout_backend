package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "nats")
@Data
public class NatsProperties {

    private String natsUrls;

    private String token = null;

    private int maxReconnect = 60;

    private int reconnectWait = 2;

    private int connectionTimeout = 2;



}

