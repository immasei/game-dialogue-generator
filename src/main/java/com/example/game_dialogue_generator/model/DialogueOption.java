package com.example.game_dialogue_generator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
public class DialogueOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String response;
    private String character;
}
