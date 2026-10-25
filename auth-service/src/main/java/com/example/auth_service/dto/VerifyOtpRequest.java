package com.example.auth_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record VerifyOtpRequest( 
    @NotBlank
    @Pattern(regexp = "^\\+[1-9]\\d{7,14}$")
    String phoneNumber,

    @NotBlank
    @Pattern(regexp = "^\\d{6}$")
    String otp
) {}
