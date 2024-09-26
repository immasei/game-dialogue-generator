package com.example.game_dialogue_generator.dto;

import com.example.game_dialogue_generator.enums.LanguageEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * choose from ENGLISH, SPANISH, GERMAN
 */
@Getter
@Setter
public class LanguageDTO {
    private LanguageEnum name;

    public LanguageDTO(LanguageEnum name) {
        this.name = name;
    }

}
