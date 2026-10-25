package com.example.auth_service.service.authenticationservice;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.example.auth_service.dto.VerifyOtpRequest;
import com.example.auth_service.model.UserInfo;
import com.example.auth_service.dto.response.AuthResponse;
import com.example.auth_service.enums.AccountStatus;
import com.example.auth_service.repo.AuthRepo;
import com.example.auth_service.service.OtpService;
import com.example.auth_service.util.JwtService;
import com.example.auth_service.id_generator.SnowflakeIdGenerator;

@Service
public class AuthenticationService {

    private final OtpService otpService;
    private final AuthRepo userRepository;
    private final JwtService jwtService;
    private final SnowflakeIdGenerator snowflakeIdGenerator;

    public AuthenticationService(
            OtpService otpService,
            AuthRepo userRepository,
            JwtService jwtService,
            SnowflakeIdGenerator snowflakeIdGenerator) {
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.snowflakeIdGenerator = snowflakeIdGenerator;
    }

    @Transactional
    public AuthResponse verifyOtpAndLogin(
            VerifyOtpRequest request) {

        // 1. Verify and consume the OTP.
        if (!otpService.verifyOtp(request)) {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid or expired OTP"
            );
        }

        // 2. Find an existing user or register a new one.
        UserInfo user = userRepository.findByPhoneNumber(request.phoneNumber())
            .orElseGet(() -> createUser(request.phoneNumber()));

        // 3. Never issue a token to a locked or disabled account.
        if (user.getAccountStatus() == AccountStatus.LOCKED
                || user.getAccountStatus() == AccountStatus.DISABLED) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Account is not allowed to log in"
            );
        }

        // A successfully verified phone number activates the account.
        user.setPhoneVerifiedAt(Instant.now());
        user.setAccountStatus(AccountStatus.ACTIVE);
        user = userRepository.save(user);

        // 4. Issue JWT with the Snowflake user ID as subject.
        String token = jwtService.generateToken(user.getUserId());

        return new AuthResponse(
            user.getUserId(),
            token,
            "Bearer",
            jwtService.getExpirationSeconds()
        );
    }

    private UserInfo createUser(String phoneNumber) {
        UserInfo user = new UserInfo();

        // Adapt nextId() to the method name in your generator.
        user.setUserId(snowflakeIdGenerator.generateId());
        user.setPhoneNumber(phoneNumber);
        user.setAccountStatus(AccountStatus.ACTIVE);
        user.setPhoneVerifiedAt(Instant.now());

        return userRepository.save(user);
    }
}