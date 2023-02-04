package com.innovator.innovator.controllers;

import com.innovator.innovator.models.chat.ChatMessage;
import com.innovator.innovator.payload.request.ChatRequest;
import com.innovator.innovator.payload.response.ChatResponse;
import com.innovator.innovator.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void sendMessageGeneral(ChatRequest chatRequest) {
        switch (chatRequest.getCommand()) {
            case "send":
                chatMessageService.sendMessage(chatRequest);
                break;

            case "delete":
                chatMessageService.deleteMessage(chatRequest.getId());
                break;

            case "reaction":
                chatMessageService.reactionMessage(chatRequest);
                break;
        }
    }

    @GetMapping
    public ResponseEntity<?> getChat(@RequestParam(defaultValue = "0") int page) {
        Page<ChatMessage> chatMessages = chatMessageService
                .findAll(PageRequest.of(page, 50, Sort.by(Sort.Direction.DESC, "id")));

        List<ChatResponse> chatResponses = new ArrayList<>();
        for (var cm : chatMessages.getContent()) {
            chatResponses.add(chatMessageService.getChatResponse(cm, null));
        }

        return ResponseEntity.ok(chatResponses);
    }
}
