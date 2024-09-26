package com.example.game_dialogue_generator.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
public class Background {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String setting;
    private String location;
    private String timePeriod;
}
