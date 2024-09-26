package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OutputMessageDTO {
    private GenreDTO genre; // Contains enum GenreEnum
    private BackgroundDTO background;
    private String plot;
    private List<CharacterDTO> characters;
    private DialogueDTO dialogue;
    private LanguageDTO language; // Contains enum LanguageEnum

    public OutputMessageDTO(GenreDTO genre, BackgroundDTO background, String plot,
                            List<CharacterDTO> characters, DialogueDTO dialogue, LanguageDTO language) {
        this.genre = genre;
        this.background = background;
        this.plot = plot;
        this.characters = characters;
        this.dialogue = dialogue;
        this.language = language;
    }

}
