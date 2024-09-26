package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * hard coded, if no corresponding depth from output, then use null as placeholder
 */
public class DialogueDTO {
    private DialogueDepthDTO depth1;
    private DialogueDepthDTO depth2_1;
    private DialogueDepthDTO depth2_2;
    private DialogueDepthDTO depth2_3;
    private DialogueDepthDTO depth3_1_1;
    private DialogueDepthDTO depth3_1_2;
    private DialogueDepthDTO depth3_1_3;
    private DialogueDepthDTO depth3_2_1;
    private DialogueDepthDTO depth3_2_2;
    private DialogueDepthDTO depth3_2_3;
    private DialogueDepthDTO depth3_3_1;
    private DialogueDepthDTO depth3_3_2;
    private DialogueDepthDTO depth3_3_3;

    // Manual constructor
    public DialogueDTO(DialogueDepthDTO depth1, DialogueDepthDTO depth2_1, DialogueDepthDTO depth2_2, DialogueDepthDTO depth2_3,
                       DialogueDepthDTO depth3_1_1, DialogueDepthDTO depth3_1_2, DialogueDepthDTO depth3_1_3,
                       DialogueDepthDTO depth3_2_1, DialogueDepthDTO depth3_2_2, DialogueDepthDTO depth3_2_3,
                       DialogueDepthDTO depth3_3_1, DialogueDepthDTO depth3_3_2, DialogueDepthDTO depth3_3_3) {
        this.depth1 = depth1;
        this.depth2_1 = depth2_1;
        this.depth2_2 = depth2_2;
        this.depth2_3 = depth2_3;
        this.depth3_1_1 = depth3_1_1;
        this.depth3_1_2 = depth3_1_2;
        this.depth3_1_3 = depth3_1_3;
        this.depth3_2_1 = depth3_2_1;
        this.depth3_2_2 = depth3_2_2;
        this.depth3_2_3 = depth3_2_3;
        this.depth3_3_1 = depth3_3_1;
        this.depth3_3_2 = depth3_3_2;
        this.depth3_3_3 = depth3_3_3;
    }

    // Default constructor
    public DialogueDTO() {}
}
