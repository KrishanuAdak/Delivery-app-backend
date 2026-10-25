package com.example.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.auth_service.dto.OtpRequest;
import com.example.auth_service.dto.VerifyOtpRequest;
import com.example.auth_service.dto.response.AuthResponse;
import com.example.auth_service.service.OtpService;
import com.example.auth_service.service.authenticationservice.AuthenticationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("/api/v1/otp")
public class OtpController {
    private final AuthenticationService authService;
    private final OtpService otpService;

    public OtpController(OtpService otpService, AuthenticationService authService) {
        this.otpService = otpService;
        this.authService=authService;
    }

    @PostMapping ("/send")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody OtpRequest request) {
        this.otpService.sendAndStoreOtp(request);
        return ResponseEntity.ok("OTP sent successfully");
    } 

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        AuthResponse response = authService.verifyOtpAndLogin(request);
        return ResponseEntity.ok(response);
    }
}

