package com.innovator.innovator.services;

import com.innovator.innovator.models.chat.ChatMessage;
import com.innovator.innovator.models.chat.Reaction;
import com.innovator.innovator.models.chat.ReactionMessage;
import com.innovator.innovator.payload.request.ChatRequest;
import com.innovator.innovator.payload.response.ChatResponse;
import com.innovator.innovator.repository.ChatMessageRepository;
import com.innovator.innovator.repository.ReactionMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
//@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ReactionMessageRepository reactionMessageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatMessage save(ChatMessage cm) {
        return chatMessageRepository.save(cm);
    }

    public List<ChatMessage> findAll() {
        return chatMessageRepository.findAll();
    }

    public ChatMessage findById(int id) {
        return chatMessageRepository.findById(id).get();
    }

    public Page<ChatMessage> findAll(Pageable p) {
        return chatMessageRepository.findAll(p);
    }

    public void deleteById(int id) {
        chatMessageRepository.deleteById(id);
    }

    public void sendMessage(ChatRequest chatMessage) {
        ChatMessage cm = new ChatMessage();
        cm.setText(chatMessage.getText());
        cm.setDate(Instant.now());
        cm.setLogin(chatMessage.getLogin());
        cm.setAvatar(chatMessage.getAvatar());

        cm = chatMessageRepository.save(cm);
        System.out.println(getChatResponse(cm, "send"));
        simpMessagingTemplate.convertAndSend("/topic/messages", getChatResponse(cm, "send"));
    }

    public void deleteMessage(int id) {
        chatMessageRepository.deleteById(id);
        simpMessagingTemplate.convertAndSend("/topic/messages", Map.of(
                "id", id,
                "command", "delete"));
    }

    public void reactionMessage(ChatRequest chatRequest) {
        ChatMessage chatMessage = chatMessageRepository.findById(chatRequest.getId()).get();

        Optional<ReactionMessage> reactionMessage = reactionMessageRepository
                .findByLoginAndChatMessage(chatRequest.getLogin(), chatMessage);


        if (reactionMessage.isPresent()) {
            if (chatRequest.getReaction().equals(reactionMessage.get().getReaction())) {
                reactionMessageRepository.delete(reactionMessage.get());
            } else {
                reactionMessage.get().setReaction(chatRequest.getReaction());
                reactionMessageRepository.save(reactionMessage.get());
            }
        } else {
            ReactionMessage rm = new ReactionMessage();
            rm.setReaction(chatRequest.getReaction());
            rm.setLogin(chatRequest.getLogin());
            rm.setChatMessage(chatMessage);

            reactionMessageRepository.save(rm);
        }
        System.out.println(getChatResponse(chatMessage, "reaction"));
        simpMessagingTemplate.convertAndSend("/topic/messages", getChatResponse(chatMessage, "reaction"));
    }

    public ChatResponse getChatResponse(ChatMessage cm, String command) {
        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setLogin(cm.getLogin());
        chatResponse.setId(cm.getId());
        chatResponse.setDate(cm.getDate());
        chatResponse.setText(cm.getText());
        chatResponse.setAvatar(cm.getAvatar());
        chatResponse.setCommand(command);

        if (cm.getReactionMessages() != null) {
            for (var reaction : reactionMessageRepository.findAllByChatMessage(cm)) {
                switch (reaction.getReaction()) {
                    case LIKE:
                        chatResponse.getLike().add(reaction.getLogin());
                        break;
                    case DISLIKE:
                        chatResponse.getDislike().add(reaction.getLogin());
                        break;
                    case FIRE:
                        chatResponse.getFire().add(reaction.getLogin());
                        break;
                    case RED_HEART:
                        chatResponse.getRedHeart().add(reaction.getLogin());
                }
            }
        }

        return chatResponse;
    }

//    public ReactionMessage setReactionMessage(ReactionMessage reactionMessage) {
//
//        List<ReactionMessage> reactionMessageList = reactionMessageRepository.findAll();
//
//        for (var reaction: reactionMessageList) {
//            switch (reactionMessage.getReaction()) {
//                case LIKE:
//                    if (reactionMessage.getLogin())
//            }
//        }
//
//        switch (reactionMessage.getReaction()) {
//            case LIKE:
//
//        }
//
//
//    }
}
