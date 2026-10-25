package com.example.auth_service.service.producerservice;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.auth_service.enums.EventsName.OtpEventTypes;
import com.example.auth_service.kafka_events.otp_events.Otp_event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service 
public class KafkaProducerService {
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public KafkaProducerService(KafkaTemplate<String,String> template,ObjectMapper objectMapper){
        this.kafkaTemplate=template;
        this.objectMapper=objectMapper;
    }
    @SuppressWarnings("null")
    public void publishToBroker(Otp_event events,OtpEventTypes topic){
        try{
            String json=objectMapper.writeValueAsString(events);
            kafkaTemplate.send(topic.name(), events.phone_number(), json).join();

        }
        catch(JsonProcessingException e){
            throw new IllegalStateException("Failed to serialize!");

        }
        catch(RuntimeException e){
            throw new IllegalStateException("Failed to publish event");
            

        }

    }
    




}
