package com.example.chat_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.example.chat_service.dto.UserSessionDetails;

@Configuration 
public class RedisConfiguration {
    
    @Bean 
    public RedisTemplate<String, UserSessionDetails> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, UserSessionDetails> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        Jackson2JsonRedisSerializer<UserSessionDetails> serializer = new Jackson2JsonRedisSerializer<>(UserSessionDetails.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;

    } 

}
