package com.innovator.innovator.models.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReactionMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;

    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @ManyToOne
    private ChatMessage chatMessage;
}
