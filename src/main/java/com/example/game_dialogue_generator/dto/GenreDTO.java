package com.example.game_dialogue_generator.dto;

import com.example.game_dialogue_generator.enums.GenreEnum;
import lombok.Getter;
import lombok.Setter;


/**
 * choose from FANTASY, ADVENTURE, ROMANCE, HORROR, ACTION
 */
@Getter
@Setter
public class GenreDTO {
    private GenreEnum name;

    public GenreDTO(GenreEnum name) {
        this.name = name;
    }

}
