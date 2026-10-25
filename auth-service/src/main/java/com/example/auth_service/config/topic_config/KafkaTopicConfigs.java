package com.example.auth_service.config.topic_config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration 
public class KafkaTopicConfigs {
        @Bean
    public NewTopic otpSmsRequestsTopic() {
        return TopicBuilder.name("otp-sms-requests")
                .partitions(3)
                .replicas(1)
                .config("min.insync.replicas", "1")
                .config("retention.ms", "86400000")
                .config("cleanup.policy", "delete")
                .build();
    }
    @Bean
    public NewTopic otpSmsRequestsDltTopic() {
        return TopicBuilder.name("otp-sms-requests-dlt")
                .partitions(3)
                .replicas(1)
                .config("min.insync.replicas", "1")
                .config("retention.ms", "604800000")
                .config("cleanup.policy", "delete")
                .build();
    }


}
