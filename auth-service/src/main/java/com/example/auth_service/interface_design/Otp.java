package com.example.auth_service.interface_design;

import com.example.auth_service.dto.OtpRequest;

public interface Otp {
    public String generateOtp();
    public void sendOtp(OtpRequest request);   
}
