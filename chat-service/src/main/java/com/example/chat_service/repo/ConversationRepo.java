package com.example.chat_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.chat_service.model.OneToOne_Message_Model.Conversation;
@Repository 
public interface ConversationRepo extends JpaRepository<Conversation, Long> {
    @Query("SELECT c FROM Conversation c WHERE (c.userId1 = :senderId AND c.userId2 = :receiverId) OR (c.userId1 = :receiverId AND c.userId2 = :senderId)")
    Optional<Conversation> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

}
