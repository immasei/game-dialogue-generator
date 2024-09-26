package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DialogueOptionDTO {
    private String response;
    private String character;

    public DialogueOptionDTO(String response, String character) {
        this.response = response;
        this.character = character;
    }
}
