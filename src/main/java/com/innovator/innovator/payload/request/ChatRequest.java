package com.innovator.innovator.payload.request;

import com.innovator.innovator.models.chat.Reaction;
import lombok.Data;

import java.time.Instant;

@Data
public class ChatRequest {

    private Integer id;

    private String text;
    private String login;
    private Instant date;
    private String avatar;
    private String command;
    private Reaction reaction;
}
