package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDTO {
    private String name;
    private String personality;
    private String speechFeatures; //tonality, words they prefer etc, in a sentence for for chatgpt api to read

    public CharacterDTO(String name, String personality, String speechFeatures) {
        this.name = name;
        this.personality = personality;
        this.speechFeatures = speechFeatures;
    }

}
