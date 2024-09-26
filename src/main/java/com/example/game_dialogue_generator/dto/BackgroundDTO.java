package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackgroundDTO {
    private String setting;
    private String location;
    private String timePeriod;

    public BackgroundDTO(String setting, String location, String timePeriod) {
        this.setting = setting;
        this.location = location;
        this.timePeriod = timePeriod;
    }

}
