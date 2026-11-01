package com.example.chat_service.model.OneToOne_Message_Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "messages")
public class MessageInfo {
    @Id 
    private long id;
    @NotEmpty 
    private String messages;
    @NotNull
    private Long conversationId;
    @PrePersist
    private void prePersist() {
        sendTime = LocalDateTime.now();
    }
    private LocalDateTime sendTime;

    @PreUpdate 
    private void preUpdate(){
        updatedTime=LocalDateTime.now();
    }
    private LocalDateTime updatedTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return messages;
    }

    public void setMessage(String messages) {
        this.messages = messages;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
    
    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    
    public MessageInfo() {
    }

    public MessageInfo(long id, String messages, Long conversationId, LocalDateTime sendTime, LocalDateTime updatedTime) {
        this.id = id;
        this.messages = messages;
        this.conversationId = conversationId;
        this.sendTime = sendTime;
        this.updatedTime = updatedTime;
    }
    
}


