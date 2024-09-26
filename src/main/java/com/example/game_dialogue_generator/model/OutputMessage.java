package com.example.game_dialogue_generator.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
public class OutputMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "genreid", nullable = false)
    private Genre genre;

    @OneToOne(cascade = CascadeType.ALL)
    private Background background; // background class

    private String plot;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Character> characters; // list of chars

    @OneToOne(cascade = CascadeType.ALL)
    private Dialogue dialogue; // container for n depth m width dialogue options

    @ManyToOne
    @JoinColumn(name = "languageid", nullable = false)
    private Language language;
}
