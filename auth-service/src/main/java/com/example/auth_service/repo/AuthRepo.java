package com.example.auth_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.auth_service.model.UserInfo;

@Repository
public interface AuthRepo extends JpaRepository<UserInfo, Long> {
    @Query (value="SELECT phone_number,account_status FROM users_info WHERE phone_number = ?1", nativeQuery = true)
    public Optional<UserInfo> findByPhoneNumber(String phoneNumber);
    public boolean existsByPhoneNumber(String phoneNumber);
}
