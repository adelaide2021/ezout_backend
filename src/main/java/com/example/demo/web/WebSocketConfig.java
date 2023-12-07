package com.example.demo.web;

import com.example.demo.receive.MessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Configuring WebSocket handlers, interceptors, and cross-origin resource sharing (CORS)
        registry.addHandler(myHandler(), "/ws/{pid}").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MessageListener();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);  // Setting maximum buffer size for text messages
        container.setMaxBinaryMessageBufferSize(8192);  // Setting maximum buffer size for binary messages
        container.setMaxSessionIdleTimeout(3L * 60 * 1000); // Setting the maximum idle timeout for WebSocket sessions (3 minutes)
        container.setAsyncSendTimeout(10L * 1000); // Setting the asynchronous send timeout
        return container;
    }
}
