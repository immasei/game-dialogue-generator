package com.example.game_dialogue_generator.model;

import com.example.game_dialogue_generator.enums.GenreEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Genre {
    // The attributes of the Genre table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreid;

    @Column(nullable = false, unique = true, length = 25)
    @Enumerated(EnumType.STRING)
    private GenreEnum name;
}
