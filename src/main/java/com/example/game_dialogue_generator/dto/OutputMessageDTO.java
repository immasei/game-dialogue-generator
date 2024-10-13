package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * OutputMessageDTO represents the structure of the generated dialogue output.
 * - Dialogue structure for each depth:
 *   - The dialogue at each depth is represented in the following format:
 *     1. Dialogue line spoken by the NPC
 *     2. Name of the character speaking (NPC)
 *     3. Option 1 response text (Player)
 *     4. Option 2 response text (Player)
 *     5. Option 3 response text (Player)
 */
@Getter
@Setter
public class OutputMessageDTO {

    private Long id;  // PK
    private Integer userId;  // Foreign key referencing User

    // dialogue structure
    // 1. Dialogue line spoken by the NPC character
    // 2. Name of the NPC character speaking
    // 3. Option 1 response text (Player)
    // 4. Option 2 response text (Player)
    // 5. Option 3 response text (Player)
    private List<String> depth1;

    // Dialogue for depth 2, branching from depth 1 options
    private List<String> depth2_1;
    private List<String> depth2_2;
    private List<String> depth2_3;

    // Dialogue for depth 3, branching from depth 2 options
    private List<String> depth3_1_1;
    private List<String> depth3_1_2;
    private List<String> depth3_1_3;

    private List<String> depth3_2_1;
    private List<String> depth3_2_2;
    private List<String> depth3_2_3;

    private List<String> depth3_3_1;
    private List<String> depth3_3_2;
    private List<String> depth3_3_3;
}
