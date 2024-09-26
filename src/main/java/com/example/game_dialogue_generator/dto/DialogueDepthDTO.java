package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DialogueDepthDTO {
    private String character;
    private String line;
    private Map<String, DialogueOptionDTO> options;

    public DialogueDepthDTO(String character, String line, Map<String, DialogueOptionDTO> options) {
        this.character = character;
        this.line = line;
        this.options = options;
    }

}
