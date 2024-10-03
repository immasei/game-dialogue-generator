package com.example.game_dialogue_generator.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * OpenAIRequestDTO is used to structure the data being sent to the OpenAI API for dialogue generation.
 *
 * Fields:
 * - Core game information:
 *   - genre: Genre of the game (e.g., Fantasy, Sci-Fi, etc.)
 *   - language: Language of the dialogue output (e.g., English, French, etc.)
 *   - setting: General setting or environment (e.g., city, region, world)
 *   - location: Specific location within the setting (e.g., Mondstadt Region)
 *   - timePeriod: Time of day or period (e.g., Day, Night, Medieval Era, etc.)
 *   - plot: Brief description of the main storyline or event driving the dialogue
 *
 * - Character details:
 *   - characterNames: List of character names (Player at index 0, NPC at index 1)
 *   - characterPersonalities: List of personality traits corresponding to each character
 *   - characterSpeechFeatures: List of speech styles corresponding to each character
 *   Note: Each index in characterNames characterPersonalities and characterSpeechFeatures refers to the same character.
 *   i.e. If characterNames.get(0) is Rover, then characterPersonalities.get(0) and
 *         characterSpeechFeatures.get(0) describe Rover's personality and speech style respectively.
 */
@Getter
@Setter
public class OpenAIRequestDTO {

    // Core game information
    private String genre;
    private String language;
    private String setting;
    private String location;
    private String timePeriod;
    private String plot;

    // Character details (must be exactly 2 characters for the dialogue: Player and NPC)
    private List<String> characterNames;          // E.g., ["Trailblazer (Player)", "Venti (NPC)"]
    private List<String> characterPersonalities;  // E.g., ["Like trash cans", "drunk bard"]
    private List<String> characterSpeechFeatures; // E.g., ["refers to herself as the galatic baseballer, and her catchphrase is rules are meant to be broken", "Casual and poetic, likes to say ehe when awkward"]

}
