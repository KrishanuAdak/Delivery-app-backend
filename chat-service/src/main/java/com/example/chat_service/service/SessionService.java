package com.example.chat_service.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.chat_service.dto.UserSessionDetails;

@Service
public class SessionService {

    private final RedisTemplate<String, UserSessionDetails> redisTemplate;

    public SessionService(RedisTemplate<String, UserSessionDetails> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveSession(UserSessionDetails sessionDetails){
        String key="session:"+sessionDetails.getSessionId()+"chat-server-userid:"+sessionDetails.getUserId();
        redisTemplate.opsForValue().set(key, sessionDetails);
    }
    public UserSessionDetails getSession(String sessionId, String userId){
        String key="session:"+sessionId+"chat-server-userid:"+userId;
        return redisTemplate.opsForValue().get(key);
    }
    public void deleteSession(String sessionId, String userId){
        String key="session:"+sessionId+"chat-server-userid:"+userId;
        redisTemplate.delete(key);
    }

}
