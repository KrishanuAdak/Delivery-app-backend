package com.example.auth_service.dto.response;

public record AuthResponse(Long userId,
    String accessToken,
    String tokenType,
    long expiresIn
) {


}
