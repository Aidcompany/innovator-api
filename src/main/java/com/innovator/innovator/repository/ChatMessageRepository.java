package com.innovator.innovator.repository;

import com.innovator.innovator.models.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Integer>, PagingAndSortingRepository<ChatMessage, Integer> {
}
