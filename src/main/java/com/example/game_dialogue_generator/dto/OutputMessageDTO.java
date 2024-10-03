package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OutputMessageDTO {
    private Long id;
    private String genre;
    private String language;
    private String setting;
    private String location;
    private String timePeriod;
    private String plot;
    private List<String> characterNames;
    private List<String> characterPersonalities;
    private List<String> characterSpeechFeatures;
    private List<String> depth1;
    private List<String> depth2_1;
    private List<String> depth2_2;
    private List<String> depth2_3;
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
