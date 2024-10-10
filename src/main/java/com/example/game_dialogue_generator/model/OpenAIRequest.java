package com.example.game_dialogue_generator.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Model class for OpenAI request.
 */
@Entity
@Getter
@Setter
public class OpenAIRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Core game information
    private String genre;
    private String language;
    private String setting;
    private String location;
    private String timePeriod;
    private String plot;

    // Depth and width for dialogue branching
    @Column(nullable = false)
    private int depth = 1; // depth of the dialogue, default is 1, up to 3

    @Column(nullable = false)
    private int width = 1; // width of the dialogue - number of options, default is 1, up to 3

    // Each index in characterNames, characterPersonalities, and characterSpeechFeatures corresponds to the same character.
    // i.e if characterNames.get(0) is Alex then characterPersonalities.get(0) and characterSpeechFeatures.get(0)
    // describe Alex's personality and speech features respectively.
    // Note: the first character is the player, the 2nd character is the NPC.
    // It should strictly have 2 characters to ensure OpenAI doesn't provide unexpected outputs.
    @ElementCollection
    @CollectionTable(name = "character_names", joinColumns = @JoinColumn(name = "openai_request_id"))
    @Column(name = "character_name")
    private List<String> characterNames;

    @ElementCollection
    @CollectionTable(name = "character_personalities", joinColumns = @JoinColumn(name = "openai_request_id"))
    @Column(name = "character_personality")
    private List<String> characterPersonalities;

    @ElementCollection
    @CollectionTable(name = "character_speech_features", joinColumns = @JoinColumn(name = "openai_request_id"))
    @Column(name = "character_speech_feature")
    private List<String> characterSpeechFeatures;
}
