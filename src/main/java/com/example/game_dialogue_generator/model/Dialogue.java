package com.example.game_dialogue_generator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
/**
 * has hard coded number of depths and options to make it easier
 */
public class Dialogue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Depth 1 Dialogue Levels
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_1;

    // Depth 2 Dialogue Levels
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_2_1;
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_2_2;
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_2_3;

    // Depth 3 Dialogue Levels
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_1_1;
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_1_2;
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_1_3;

    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_2_1;
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_2_2;
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_2_3;

    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_3_1;
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_3_2;
    @OneToOne(cascade = CascadeType.ALL)
    private DialogueDepth depth_3_3_3;
}
