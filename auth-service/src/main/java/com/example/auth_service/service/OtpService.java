package com.example.auth_service.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import com.example.auth_service.dto.OtpRequest;
import com.example.auth_service.dto.VerifyOtpRequest;
import com.example.auth_service.enums.EventsName.OtpEventTypes;
import com.example.auth_service.interface_design.Otp;
import com.example.auth_service.kafka_events.otp_events.Otp_event;
import com.example.auth_service.service.producerservice.KafkaProducerService;

@Service
public class OtpService implements Otp {
    private final StringRedisTemplate redisTemplate;
    private final KafkaProducerService kafkaProducerService;
    private final SecureRandom secureRandom = new SecureRandom();

    public OtpService(StringRedisTemplate redisTemplate, KafkaProducerService producerService) {
        this.redisTemplate = redisTemplate;
        this.kafkaProducerService = producerService;
    }

    private static final DefaultRedisScript<Long> VERIFY_AND_DELETE = new DefaultRedisScript<>(
            "if redis.call('GET', KEYS[1]) == ARGV[1] " +
                    "then return redis.call('DEL', KEYS[1]) " +
                    "else return 0 end",
            Long.class);

    @Override
    public String generateOtp() {
        return this.createOtp();
    }

    @Override
    public void sendOtp(OtpRequest request) {
        this.sendAndStoreOtp(request);
    }

    private String createOtp() {
        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1_000_000));
        return otp;
    }

    @SuppressWarnings("null")
    public void sendAndStoreOtp(OtpRequest request) {
        String otp = generateOtp();
        String key = "otp:" + request.phone_number;
        redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(5));
        Otp_event event_to_publish = new Otp_event(request.phone_number, otp);
        kafkaProducerService.publishToBroker(event_to_publish, OtpEventTypes.OTP_SENT);
    }

    public boolean verifyOtp(VerifyOtpRequest request) {
        return this.isVerifiedOtp(request);
    }

    @SuppressWarnings("null")
    public boolean isVerifiedOtp(VerifyOtpRequest request) {
        if (request == null
                || request.phoneNumber() == null
                || request.otp() == null
                || !request.otp().matches("\\d{6}")) {
            return false;
        }

        String key = "otp:" + request.phoneNumber();
        Long result = redisTemplate.execute(
                VERIFY_AND_DELETE,
                List.of(key),
                request.otp());

        return Long.valueOf(1L).equals(result);

    }

}
