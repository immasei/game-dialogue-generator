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
 * - Dialogue branching details:
 *   - depth: Depth of the dialogue, default is 1, up to 3.
 *   - width: Width of the dialogue branching, default is 1, up to 3.
 *
 * - Character details:
 *   - characterNames: List of character names (Player at index 0, NPC at index 1)
 *   - characterPersonalities: List of personality traits corresponding to each character
 *   - characterSpeechFeatures: List of speech styles corresponding to each character
 *   Note: Each index in characterNames characterPersonalities and characterSpeechFeatures refers to the same character.
 *   i.e. If characterNames.get(0) is Rover, then characterPersonalities.get(0) and
 *         characterSpeechFeatures.get(0) describe Rover's personality and speech style respectively.
 *
 * - User information:
 *   - userId: The ID of the user creating the request.
 */
@Getter
@Setter
public class OpenAIRequestDTO {

    private Long id;  // PK
    private Integer userId;  // Foreign key referencing User

    // Core game information
    private String genre;
    private String language;
    private String setting;
    private String location;
    private String timePeriod;
    private String plot;

    // Dialogue branching details
    private int depth = 1;  // Depth of the dialogue, default is 1, up to 3
    private int width = 1;  // Width of the dialogue - number of options, default is 1, up to 3

    // Character details (must be exactly 2 characters for the dialogue: Player and NPC)
    private List<String> characterNames;          // E.g., ["Trailblazer (Player)", "Venti (NPC)"]
    private List<String> characterPersonalities;  // E.g., ["Likes trash cans", "Drunk bard"]
    private List<String> characterSpeechFeatures; // E.g., ["refers to herself as the galactic baseballer, and her catchphrase is rules are meant to be broken", "Casual and poetic, likes to say 'ehe' when awkward"]
}
