package com.example.chat_service.dto;

public class MessageRequest {
    private String message;
    private long senderId;
    private long receiverId;

    public MessageRequest(String message, long senderId, long receiverId) {
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }
    public MessageRequest() {
    }

    public String getMessage() {
        return message;
    }

    public long getSenderId() {
        return senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }
}