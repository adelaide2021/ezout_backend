package com.example.demo.web;

import com.example.demo.receive.MessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //配置handler,拦截器和跨域
        registry.addHandler(myHandler(), "/ws/{pid}").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MessageListener();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);  //文本消息最大缓存
        container.setMaxBinaryMessageBufferSize(8192);  //二进制消息大战缓存
        container.setMaxSessionIdleTimeout(3L * 60 * 1000); // 最大闲置时间，3分钟没动自动关闭连接
        container.setAsyncSendTimeout(10L * 1000); //异步发送超时时间
        return container;
    }
}
