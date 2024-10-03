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
 * Controller for OpenAI API calls.
 * Note: update is not supported as to update you just make a new request.
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
        OutputMessage createdOutputMessage = openAIRequestService.createOpenAIRequest(openAIRequest);
        return ResponseEntity.ok(createdOutputMessage);
    }

    // Get all OutputMessages
    @GetMapping("/outputmessages")
    public ResponseEntity<Iterable<OutputMessage>> getAllOutputMessages() {
        return ResponseEntity.ok(outputMessageService.getAllOutputMessages());
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

        // Call OpenAI API and retrieve the output message
        OutputMessage response = openAIRequestService.createOpenAIRequest(mockRequest);

        return ResponseEntity.ok(response);
    }

}
