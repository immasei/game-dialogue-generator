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

    // FK to user id
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    // dialogue structure per depth:
    // 1. Dialogue line spoken by the character
    // 2. Name of the character speaking (NPC)
    // 3. Option 1 response text (Player)
    // 4. Option 2 response text (Player)
    // 5. Option 3 response text (Player)
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
