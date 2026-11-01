package com.example.chat_service.service;

import org.springframework.context.annotation.Description;

@Description("""
    This interface defines methods for saving message details to the database.
    1. saveConversationDetailsToDB: Saves the details of a one-to-one conversation message to the database.
    2. saveGroupMessageDetailsToDB: Saves the details of a group message to the database.
    """)
public interface MessageDetailsToDB {

    long saveConversationDetailsToDB(
            String message,
            Long senderId,
            Long receiverId
    ) throws Exception;

    void saveGroupMessageDetailsToDB(
            String message,
            Long conversationId
    ) throws Exception;
}