package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.model.OpenAIRequest;
import com.example.game_dialogue_generator.model.OutputMessage;
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
 * @see OpenAIRequest
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
    public ResponseEntity<OutputMessage> createOpenAIRequest(@RequestBody OpenAIRequest openAIRequest) {
        if (openAIRequest.getDepth() < 1 || openAIRequest.getDepth() > 3 || openAIRequest.getWidth() < 1 || openAIRequest.getWidth() > 3) {
            return ResponseEntity.badRequest().body(null);
        }
        OutputMessage createdOutputMessage = openAIRequestService.createOpenAIRequest(openAIRequest);
        return ResponseEntity.ok(createdOutputMessage);
    }

    // Get request by ID
    @GetMapping("/{id}")
    public ResponseEntity<OpenAIRequest> getRequestById(@PathVariable Long id) {
        try {
            OpenAIRequest request = openAIRequestService.getRequestById(id);
            return ResponseEntity.ok(request);
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
    public ResponseEntity<OutputMessage> testOpenAI() {
        OpenAIRequest mockRequest = new OpenAIRequest();
        mockRequest.setGenre("scifi, fantasy");
        mockRequest.setSetting("Planet of Penacony - the land of dreams (from the Honkai: Star Rail universe)");
        mockRequest.setLocation("Penacony Grand Hotel");
        mockRequest.setTimePeriod("Day");
        mockRequest.setLanguage("ENGLISH");  // Setting the language value
        mockRequest.setPlot("The Trailblazer is attempting to check in at the hotel, but she is not on the invite list.");
        mockRequest.setCharacterNames(List.of("Trailblazer", "Hotel Manager"));
        mockRequest.setCharacterPersonalities(List.of("curious, slightly dumb", "polite and patient"));
        mockRequest.setCharacterSpeechFeatures(List.of(
                "refers to herself as the galactic baseballer, likes trash cans, catchphrase: rules are meant to be broken",
                "polite speech, attempting to explain how she cannot let the Trailblazer check in if she is not on the guest list"));
        mockRequest.setDepth(2);
        mockRequest.setWidth(2);

        // Call OpenAI API and retrieve the output message
        OutputMessage response = openAIRequestService.createOpenAIRequest(mockRequest);

        return ResponseEntity.ok(response);
    }

    // Test getting raw json string
    @GetMapping("/test/string")
    public ResponseEntity<String> getRawContentFromChatGPT() {
        OpenAIRequest mockRequest = new OpenAIRequest();
        mockRequest.setGenre("scifi, fantasy");
        mockRequest.setSetting("Planet of Penacony - the land of dreams");
        mockRequest.setLocation("Penacony Grand Hotel");
        mockRequest.setTimePeriod("Day");
        mockRequest.setLanguage("English");
        mockRequest.setPlot("The Trailblazer is attempting to check in at the hotel, but she is not on the invite list.");
        mockRequest.setCharacterNames(List.of("Trailblazer", "Hotel Manager"));
        mockRequest.setCharacterPersonalities(List.of("curious, slightly dumb", "polite and patient"));
        mockRequest.setCharacterSpeechFeatures(List.of(
                "refers to herself as the galactic baseballer, likes trash cans, catchphrase: rules are meant to be broken",
                "polite speech, attempting to explain how she can't let the Trailblazer check in if she's not on the guest list"
        ));
        mockRequest.setDepth(3);
        mockRequest.setWidth(2);

        String content = openAIRequestService.getOpenAIResponseContent(mockRequest);
        return ResponseEntity.ok(content);
    }
}
