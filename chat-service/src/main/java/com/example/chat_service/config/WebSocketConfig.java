package com.example.chat_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.chat_service.socketHandler.ChatSocketHandler;
@Configuration
@EnableWebSocket 
public class WebSocketConfig implements WebSocketConfigurer{
    private final ChatSocketHandler chatSocketHandler;

    public WebSocketConfig(ChatSocketHandler chatSocketHandler) {
        this.chatSocketHandler = chatSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatSocketHandler, "/chat").
        setAllowedOrigins("*");
    }
    

}
