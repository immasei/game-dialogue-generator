package com.example.game_dialogue_generator.model;

import com.example.game_dialogue_generator.enums.LanguageEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer languageid;

    @Column(nullable = false, unique = true, length = 25)
    @Enumerated(EnumType.STRING)
    private LanguageEnum name;
}
