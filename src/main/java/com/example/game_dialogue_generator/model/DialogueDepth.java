package com.example.game_dialogue_generator.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Map;

@Entity
@Getter
@Setter
public class DialogueDepth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String character;
    private String line;

    // Dialogue options (option_1, option_2, option_3)
    @ElementCollection
    @CollectionTable(name = "dialogue_options", joinColumns = @JoinColumn(name = "dialogue_depth_id"))
    @MapKeyColumn(name = "option_key")
    @Column(name = "option_value")
    private Map<String, DialogueOption> options;
}
