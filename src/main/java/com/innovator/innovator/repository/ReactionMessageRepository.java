package com.innovator.innovator.repository;

import com.innovator.innovator.models.chat.ChatMessage;
import com.innovator.innovator.models.chat.ReactionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionMessageRepository extends JpaRepository<ReactionMessage, Integer> {

    Optional<ReactionMessage> findByLoginAndChatMessage(String login, ChatMessage chatMessage);

    List<ReactionMessage> findAllByChatMessage(ChatMessage chatMessage);
}
