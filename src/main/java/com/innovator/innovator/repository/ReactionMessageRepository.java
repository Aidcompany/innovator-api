package com.innovator.innovator.repository;

import com.innovator.innovator.models.chat.ReactionMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionMessageRepository extends JpaRepository<ReactionMessage, Integer> {
}
