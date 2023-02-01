package com.innovator.innovator.models.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    private String text;
    private String login;
    private Instant date;
    private String avatar;

    @OneToMany(mappedBy = "chatMessage", cascade = CascadeType.ALL)
    private List<ReactionMessage> like;
}
