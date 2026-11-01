
package com.example.chat_service.socketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.chat_service.dto.MessageRequest;
import com.example.chat_service.dto.UserSessionDetails;
import com.example.chat_service.service.MessageInfoService;
import com.example.chat_service.service.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ChatSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final MessageInfoService messageInfoService;
    private final SessionService sessionService;
    private final RedisTemplate<String, UserSessionDetails> redisTemplate;

    // Actual WebSocket sessions remain in local server memory.
    private final Map<Long, WebSocketSession> sessions =
            new ConcurrentHashMap<>();

    @Value("${chat.server-id:chat-server-1}")
    private String serverId;

    public ChatSocketHandler(
            ObjectMapper objectMapper,
            MessageInfoService messageInfoService,
            SessionService sessionService,
            RedisTemplate<String, UserSessionDetails> redisTemplate) {

        this.objectMapper = objectMapper;
        this.messageInfoService = messageInfoService;
        this.sessionService = sessionService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {

        System.out.println(
                "WebSocket connected: " + session.getId());

        // Register the authenticated user here when JWT
        // authentication is integrated into the handshake.
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session, TextMessage message)
            throws Exception {

        MessageRequest chatMessage = objectMapper.readValue(
                message.getPayload(), MessageRequest.class);

        long senderId = chatMessage.getSenderId();
        long receiverId = chatMessage.getReceiverId();

        // Register sender's active connection locally.
        sessions.put(senderId, session);

        // Save connection metadata in Redis through SessionService.
        UserSessionDetails senderDetails = new UserSessionDetails();
        senderDetails.setUserId(senderId);
        senderDetails.setSessionId(session.getId());
        senderDetails.setServerId(serverId);

        sessionService.saveSession(senderDetails);

        // Find the receiver's server and session metadata.
        UserSessionDetails receiverDetails =
                redisTemplate.opsForValue().get(
                        "chat-server-userid:" + receiverId);

        // Persist the message even if the receiver is offline.
        messageInfoService.saveMessage(chatMessage);

        if (receiverDetails == null) {
            System.out.println(
                    "Receiver offline: " + receiverId);
            return;
        }

        // Deliver directly only when both users are on this server.
        if (serverId.equals(receiverDetails.getServerId())) {

            WebSocketSession receiverSession =
                    sessions.get(receiverId);

            if (receiverSession != null
                    && receiverSession.isOpen()) {

                receiverSession.sendMessage(
                        new TextMessage(message.getPayload()));

            } else {
                System.out.println(
                        "Receiver session unavailable: " + receiverId);
            }

        } else {
            // TODO: Forward the message to receiverDetails.getServerId()
            // using Redis Pub/Sub or Kafka.
            System.out.println(
                    "Receiver is on another server: "
                            + receiverDetails.getServerId());
        }

        System.out.println(
                "Message received from user " + senderId
                        + " in session " + session.getId());
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session, CloseStatus status)
            throws Exception {

        // Remove only the connection that actually closed.
        sessions.entrySet().removeIf(
                entry -> entry.getValue().getId()
                        .equals(session.getId()));

        // TODO: Remove Redis metadata only if it still points
        // to this session ID, so a newer connection is not deleted.
        System.out.println(
                "WebSocket closed: " + session.getId());
    }
}
