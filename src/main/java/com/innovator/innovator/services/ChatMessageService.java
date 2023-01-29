package com.innovator.innovator.services;

import com.innovator.innovator.configs.websocket.model.ChatMessage;
import com.innovator.innovator.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatMessage save(ChatMessage cm) {
       return chatMessageRepository.save(cm);
    }

    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAll();
    }

    public void sendMessage(ChatMessage chatMessage) {
        ChatMessage cm = new ChatMessage();
        cm.setText(chatMessage.getText());
        cm.setDate(LocalDate.now());
        cm.setLogin(chatMessage.getLogin());

        chatMessageRepository.save(cm);

        simpMessagingTemplate.convertAndSend("/topic/messages", chatMessage);
    }


}
