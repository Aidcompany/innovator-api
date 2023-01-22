package com.innovator.innovator.controllers;

import com.innovator.innovator.configs.websocket.model.ChatMessage;
import com.innovator.innovator.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
//        ChatMessage cm = new ChatMessage();
//        cm.setText(chatMessage.getText());
//        cm.setDate(chatMessage.getDate());
//        cm.setLogin(chatMessage.getLogin());
//        cm.setAvatar(chatMessage.getAvatar());
//
//        chatMessageService.save(cm);
//
//        return chatMessage;
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getLogin());
//        return chatMessage;
//    }

    @MessageMapping("/chat")
    public void sendMessageGeneral(ChatMessage chatMessage) {
        chatMessageService.sendMessage(chatMessage);
    }

    @GetMapping("/getChat")
    public ResponseEntity<?> getChat() {
        return ResponseEntity.ok(chatMessageService.findAll());
    }
}
