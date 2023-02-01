package com.innovator.innovator.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private Integer id;
    private String text;
    private String login;
    private Instant date;
    private String avatar;

    private List<String> like = new ArrayList<>();
    private List<String> dislike = new ArrayList<>();

    @JsonProperty("red_heart")
    private List<String> redHeart = new ArrayList<>();
    private List<String> fire = new ArrayList<>();
}
