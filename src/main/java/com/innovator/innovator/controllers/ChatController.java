package com.innovator.innovator.controllers;

import com.innovator.innovator.models.chat.ChatMessage;
import com.innovator.innovator.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void sendMessageGeneral(ChatMessage chatMessage) {
        chatMessageService.sendMessage(chatMessage);
    }

    @MessageMapping("/changeMessage/{id}")
    @SendTo("/topic/messages")
    public ChatMessage changeMessage(@DestinationVariable int id, ChatMessage chatMessageBody) {
        ChatMessage chatMessage = chatMessageService.findById(id);
//        chatMessage.setLogin(chatMessageBody.getLogin());
        chatMessage.setText(chatMessageBody.getText());

        return chatMessageService.save(chatMessage);
    }

//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/general")
//    public ChatMessage sendMessage(ChatMessage chatMessage) {
//        return chatMessageService.save(chatMessage);
//    }

//    @GetMapping("/getChat")
//    public ResponseEntity<?> getChat() {
//        return ResponseEntity.ok(chatMessageService.findAll());
//    }
}
