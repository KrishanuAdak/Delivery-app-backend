package com.example.auth_service.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OtpRequest {
    @NotBlank
    @Pattern(regexp = "^\\+[1-9]\\d{7,14}$")
    public String phone_number;
    

}
