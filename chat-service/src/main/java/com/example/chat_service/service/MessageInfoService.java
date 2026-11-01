package com.example.chat_service.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.chat_service.dto.MessageRequest;
import com.example.chat_service.id_generator.SnowflakeIdGenerator;
import com.example.chat_service.model.OneToOne_Message_Model.Conversation;
import com.example.chat_service.model.OneToOne_Message_Model.MessageInfo;
import com.example.chat_service.repo.ConversationRepo;
import com.example.chat_service.repo.MessagesRepo;

import jakarta.transaction.Transactional;

@Service
public class MessageInfoService  implements MessageDetailsToDB{
    private final SnowflakeIdGenerator idGenerator;
    private final MessagesRepo messagesRepo;
    private final ConversationRepo conversationRepo;

    public MessageInfoService(SnowflakeIdGenerator idGenerator, MessagesRepo messagesRepo,ConversationRepo conversationRepo) {
        this.idGenerator = idGenerator;
        this.messagesRepo = messagesRepo;
        this.conversationRepo=conversationRepo;
    }
    @Transactional
    @Override
    public long saveConversationDetailsToDB(String message, Long senderId, Long receiverId) throws Exception {
        Conversation conversation=new Conversation();
        
        Optional<Conversation> existingConversation=conversationRepo.findBySenderIdAndReceiverId(senderId, receiverId);
        long conversationId=0;
        if(existingConversation.isPresent()){
            conversationId=existingConversation.get().getId();
        }
        else{
            conversation.setId(idGenerator.generateId());
            conversation.setUserId1(Math.min(senderId, receiverId));
            conversation.setUserId2(Math.max(senderId, receiverId));
            Conversation savedConversation=conversationRepo.save(conversation);
            conversationId=savedConversation.getId();
        }
        return conversationId;
    }

    @Override
    public void saveGroupMessageDetailsToDB(String message, Long conversationId) throws Exception {
        MessageInfo messageInfos=new MessageInfo();
        messageInfos.setId(idGenerator.generateId());
        messageInfos.setMessage(message);
        messageInfos.setConversationId(conversationId);
        this.messagesRepo.save(messageInfos);

    }

    public void saveMessage(MessageRequest messageRequest) throws Exception {
        String message=messageRequest.getMessage();
        Long senderId=messageRequest.getSenderId();
        Long receiverId=messageRequest.getReceiverId();
        long conversationId=this.saveConversationDetailsToDB(message, senderId, receiverId);
        this.saveGroupMessageDetailsToDB(message, conversationId);
    }


    

    

}
