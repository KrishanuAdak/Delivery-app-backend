package com.example.chat_service.model;

import com.example.chat_service.enums.MessageStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity 
@Table(name="message_status")
public class MessageStatusDetails {
    @Id 
    private long id;
    private long message_id;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    private boolean lock_version;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getMessage_id() {
        return message_id;
    }
    public void setMessage_id(long message_id) {
        this.message_id = message_id;
    }
    public MessageStatus getStatus() {
        return status;
    }
    public void setStatus(MessageStatus status) {
        this.status = status;
    }
    public boolean isLock_version() {
        return lock_version;
    }
    public void setLock_version(boolean lock_version) {
        this.lock_version = lock_version;
    }
    public MessageStatusDetails() {
    }
    public MessageStatusDetails(long id, long message_id, MessageStatus status, boolean lock_version) {
        this.id = id;
        this.message_id = message_id;
        this.status = status;
        this.lock_version = lock_version;
    }
    

}
