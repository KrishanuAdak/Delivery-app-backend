package com.example.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth_service.dto.OtpRequest;
import com.example.auth_service.service.OtpService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/v1/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping ("/send")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody OtpRequest request) {
        this.otpService.sendAndStoreOtp(request);
        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping ("/verify")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpRequest request, @RequestParam String otp) {
        boolean isValid = this.otpService.verifyOtp(request, otp);
        if (isValid) {
            return ResponseEntity.status(HttpStatus.OK).body("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }

}
