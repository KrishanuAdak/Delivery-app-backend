package com.example.auth_service.service;

import java.security.SecureRandom;
import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.auth_service.dto.OtpRequest;

@Service   
public class OtpService {
    private final StringRedisTemplate redisTemplate;
    private final SecureRandom secureRandom = new SecureRandom();
    public OtpService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @SuppressWarnings("null")
    public void sendAndStoreOtp(OtpRequest request) {
        String otp = generateOtp();
        String key="otp:"+request.phone_number;
        redisTemplate.opsForValue().set(key, otp, Duration.ofMinutes(5));
        // Implement the logic to send OTP to the provided phone number
        // This could involve generating a random OTP, storing it in a database or cache,
        // and sending it via an SMS gateway or other communication service.
    }

    private String generateOtp() {
        String otp = String.format(
            "%06d",
            secureRandom.nextInt(1_000_000)
        );
        return otp;
    }
    public boolean verifyOtp(OtpRequest request,String submittedOtp){
        String storedOtp=redisTemplate.opsForValue().get("otp:"+request.phone_number);
        if(storedOtp==null || !storedOtp.equals(submittedOtp)){
            return false;
        }
        redisTemplate.delete("otp:"+request.phone_number);
        return true;

    }
}
