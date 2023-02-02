package com.innovator.innovator.controllers;

import com.innovator.innovator.models.chat.ChatMessage;
import com.innovator.innovator.models.chat.ReactionMessage;
import com.innovator.innovator.payload.request.ChatRequest;
import com.innovator.innovator.payload.response.ChatResponse;
import com.innovator.innovator.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
//    private final ReactionMessageRepository reactionMessageRepository;

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

//    @PostMapping
//    public void t(@RequestBody ChatRequest chatRequest) {
//        chatMessageService.reactionMessage(chatRequest);
//    }

//    @MessageMapping("/reactionMessage/{id}")
////    @SendTo("/topic/messages")
//    public void changeMessage(@DestinationVariable int id, ReactionMessage reactionMessageBody) {
//        chatMessageService.reactionMessage(id, reactionMessageBody);
//    }

//    @MessageMapping("/deleteMessage/{id}")
//    @SendTo("/topic/messages")
//    public void deleteMessage (@DestinationVariable int id) {
//        chatMessageService.deleteById(id);
//    }

//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/general")
//    public ChatMessage sendMessage(ChatMessage chatMessage) {
//        return chatMessageService.save(chatMessage);
//    }

//    @PostMapping("/{id}")
//    public ResponseEntity<?> edit(@PathVariable int id, @RequestBody ReactionMessage reactionMessageBody) {
//        ChatMessage chatMessage = chatMessageService.findById(id);
//
//        Optional<ReactionMessage> reactionMessage = reactionMessageRepository
//                .findByLoginAndChatMessage(reactionMessageBody.getLogin(), chatMessage);
//
//
//        if (reactionMessage.isPresent()) {
//            if (reactionMessageBody.getReaction().equals(reactionMessage.get().getReaction())) {
//                reactionMessageRepository.delete(reactionMessage.get());
//                return ResponseEntity.ok("DELETE");
//            } else {
//                reactionMessage.get().setReaction(reactionMessageBody.getReaction());
//                return ResponseEntity.ok(reactionMessageRepository.save(reactionMessage.get()));
//            }
//        } else {
//            ReactionMessage rm = new ReactionMessage();
//            rm.setReaction(reactionMessageBody.getReaction());
//            rm.setLogin(reactionMessageBody.getLogin());
//            rm.setChatMessage(chatMessage);
//
//            return ResponseEntity.ok(reactionMessageRepository.save(rm));
//        }
//    }

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
