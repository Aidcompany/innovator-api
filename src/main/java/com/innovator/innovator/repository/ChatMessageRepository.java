package com.innovator.innovator.repository;

import com.innovator.innovator.configs.websocket.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
}
