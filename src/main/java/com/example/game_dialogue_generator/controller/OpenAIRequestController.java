package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OpenAIRequestDTO;
import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.service.OpenAIRequestService;
import com.example.game_dialogue_generator.service.OutputMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller for managing OpenAI requests.
 *
 * This controller provides operations for creating new OpenAI requests, retrieving requests by ID,
 * retrieving all OpenAI requests, and deleting requests. Additionally, it provides endpoints for testing
 * the OpenAI API response.
 *
 * Endpoints:
 * - POST /api/openai: Create a new OpenAI request and generate dialogue
 * - GET /api/openai/{id}: Get a request by ID
 * - DELETE /api/openai/{id}: Delete a request by ID
 * - GET /api/openai/test: Test OpenAI API call with a mock request
 * - GET /api/openai/test/string: Get raw JSON response string from OpenAI API
 *
 * @see OpenAIRequestDTO
 * @see OutputMessageDTO
 * @see OpenAIRequestService
 */
@RestController
@RequestMapping("/api/openai")
public class OpenAIRequestController {

    @Autowired
    private OpenAIRequestService openAIRequestService;

    @Autowired
    private OutputMessageService outputMessageService;

    // Create new request and return the generated OutputMessage
    @PostMapping
    public ResponseEntity<OutputMessageDTO> createOpenAIRequest(@RequestBody OpenAIRequestDTO openAIRequestDTO) {
        if (openAIRequestDTO.getDepth() < 1 || openAIRequestDTO.getDepth() > 3 ||
                openAIRequestDTO.getWidth() < 1 || openAIRequestDTO.getWidth() > 3) {
            return ResponseEntity.badRequest().body(null);
        }
        OutputMessageDTO createdOutputMessage = openAIRequestService.createOpenAIRequest(openAIRequestDTO);
        return ResponseEntity.ok(createdOutputMessage);
    }

    // Get request by ID
    @GetMapping("/{id}")
    public ResponseEntity<OpenAIRequestDTO> getRequestById(@PathVariable Long id) {
        try {
            OpenAIRequestDTO requestDTO = openAIRequestService.getRequestById(id);
            return ResponseEntity.ok(requestDTO);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequestById(@PathVariable Long id) {
        openAIRequestService.deleteRequestById(id);
        return ResponseEntity.ok("Request with ID " + id + " has been deleted successfully.");
    }

    // Test OpenAI API call with a mock request
    @GetMapping("/test")
    public ResponseEntity<OutputMessageDTO> testOpenAI() {
        OpenAIRequestDTO mockRequestDTO = new OpenAIRequestDTO();
        mockRequestDTO.setGenre("scifi, fantasy");
        mockRequestDTO.setSetting("Planet of Penacony - the land of dreams (from the Honkai: Star Rail universe)");
        mockRequestDTO.setLocation("Penacony Grand Hotel");
        mockRequestDTO.setTimePeriod("Day");
        mockRequestDTO.setLanguage("ENGLISH");
        mockRequestDTO.setPlot("The Trailblazer is attempting to check in at the hotel, but she is not on the invite list.");
        mockRequestDTO.setCharacterNames(List.of("Trailblazer", "Hotel Manager"));
        mockRequestDTO.setCharacterPersonalities(List.of("curious, slightly dumb", "polite and patient"));
        mockRequestDTO.setCharacterSpeechFeatures(List.of(
                "refers to herself as the galactic baseballer, likes trash cans, catchphrase: rules are meant to be broken",
                "polite speech, attempting to explain how she cannot let the Trailblazer check in if she is not on the guest list"));
        mockRequestDTO.setDepth(2);
        mockRequestDTO.setWidth(2);
        mockRequestDTO.setUserId(1);  // Assuming a test user ID

        // Call OpenAI API and retrieve the output message
        OutputMessageDTO response = openAIRequestService.createOpenAIRequest(mockRequestDTO);

        return ResponseEntity.ok(response);
    }

    // Test getting raw json string
    @GetMapping("/test/string")
    public ResponseEntity<String> getRawContentFromChatGPT() {
        OpenAIRequestDTO mockRequestDTO = new OpenAIRequestDTO();
        mockRequestDTO.setGenre("scifi, fantasy");
        mockRequestDTO.setSetting("Planet of Penacony - the land of dreams");
        mockRequestDTO.setLocation("Penacony Grand Hotel");
        mockRequestDTO.setTimePeriod("Day");
        mockRequestDTO.setLanguage("English");
        mockRequestDTO.setPlot("The Trailblazer is attempting to check in at the hotel, but she is not on the invite list.");
        mockRequestDTO.setCharacterNames(List.of("Trailblazer", "Hotel Manager"));
        mockRequestDTO.setCharacterPersonalities(List.of("curious, slightly dumb", "polite and patient"));
        mockRequestDTO.setCharacterSpeechFeatures(List.of(
                "refers to herself as the galactic baseballer, likes trash cans, catchphrase: rules are meant to be broken",
                "polite speech, attempting to explain how she can't let the Trailblazer check in if she's not on the guest list"
        ));
        mockRequestDTO.setDepth(3);
        mockRequestDTO.setWidth(2);
        mockRequestDTO.setUserId(1);  // Assuming a test user ID

        String content = openAIRequestService.getOpenAIResponseContent(mockRequestDTO);
        return ResponseEntity.ok(content);
    }
}
