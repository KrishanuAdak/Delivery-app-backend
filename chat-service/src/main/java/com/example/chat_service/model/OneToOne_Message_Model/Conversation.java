package com.example.chat_service.model.OneToOne_Message_Model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="conversation_details")
public class Conversation {
    @Id 
    private long id;
    private Long userId1;
    private Long userId2;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId1() {
        return userId1;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getUserId2() {
        return userId2;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    public Conversation() {
    }

    public Conversation(long id, Long userId1, Long userId2, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    

}
