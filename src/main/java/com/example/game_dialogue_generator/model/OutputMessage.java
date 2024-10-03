package com.example.game_dialogue_generator.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class OutputMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Flattened genre and language fields
    @Column(nullable = false, length = 50)
    private String genre;

    @Column(nullable = false, length = 25)
    private String language;

    // Background fields
    private String setting;
    private String location;
    private String timePeriod;

    @Column(columnDefinition = "TEXT")
    private String plot;

    // Each index in characterNames, characterPersonalities and characterSpeechFeatures corresponds to the same character.
    // i.e if characterNames.get(0) is Alex then characterPersonalities.get(0) and characterSpeechFeatures.get(0)
    // describe Alex's personality and speech features respectively
    @ElementCollection
    @CollectionTable(name = "character_names", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "character_name")
    private List<String> characterNames;

    @ElementCollection
    @CollectionTable(name = "character_personalities", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "character_personality")
    private List<String> characterPersonalities;

    @ElementCollection
    @CollectionTable(name = "character_speech_features", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "character_speech_feature")
    private List<String> characterSpeechFeatures;

    // Depth 1 dialogue structure:
    // 1. Dialogue line spoken by the character
    // 2. Name of the character speaking
    // 3. Option 1 response text
    // 4. Option 2 response text
    // 5. Option 3 response text
    @ElementCollection
    @CollectionTable(name = "dialogue_depth1", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth1;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth2_1", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth2_1;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth2_2", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth2_2;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth2_3", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth2_3;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_1_1", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_1_1;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_1_2", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_1_2;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_1_3", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_1_3;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_2_1", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_2_1;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_2_2", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_2_2;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_2_3", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_2_3;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_3_1", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_3_1;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_3_2", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_3_2;

    @ElementCollection
    @CollectionTable(name = "dialogue_depth3_3_3", joinColumns = @JoinColumn(name = "output_message_id"))
    @Column(name = "dialogue_line")
    private List<String> depth3_3_3;
}
