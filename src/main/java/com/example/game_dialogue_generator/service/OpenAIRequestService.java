package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.model.OpenAIRequest;
import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.repository.OpenAIRequestRepository;
import com.example.game_dialogue_generator.repository.OutputMessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class for managing OpenAI API calls.
 */
@Service
public class OpenAIRequestService {

    @Autowired
    private OpenAIRequestRepository openAIRequestRepository;

    @Autowired
    private OutputMessageRepository outputMessageRepository;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    //private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Create a new OpenAI request, call OpenAI, and map response to OutputMessage
    public OutputMessage createOpenAIRequest(OpenAIRequest openAIRequest) {
        // Save request details to the database
        openAIRequestRepository.save(openAIRequest);

        // Call the OpenAI API for generating the dialogue
        String openAIResponse = callOpenAIAPI(openAIRequest);

        // Parse OpenAI response into OutputMessage
        OutputMessage outputMessage = parseOpenAIResponse(openAIResponse);

        // Save and return the output message
        return outputMessageRepository.save(outputMessage);
    }

    // api call v2
    private String callOpenAIAPI(OpenAIRequest openAIRequest) {
        WebClient webClient = WebClient.builder()
                .baseUrl(openAiApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();

        // Prepare the JSON request body
        //String requestBody = buildOpenAIRequestBody(openAIRequest); // actual code
        String requestBody = buildOpenAIRequestBodyTest(); // test
        System.out.println(requestBody);

        // Send the POST request to OpenAI and return the response body
        Mono<String> responseMono = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

        // Block to wait for response (suitable for synchronous applications)
        String response = responseMono.block();

        return response;
    }

    // Parse the OpenAI response into OutputMessage structure
    private OutputMessage parseOpenAIResponse(String openAIResponse) {
        OutputMessage outputMessage = new OutputMessage();
        System.out.println(openAIResponse);
        try {
            JsonNode rootNode = objectMapper.readTree(openAIResponse);

            // Assuming response JSON has the exact structure
            outputMessage.setDepth1(parseDialogueDepth(rootNode, "depth1"));
            outputMessage.setDepth2_1(parseDialogueDepth(rootNode, "depth2_1"));
            outputMessage.setDepth2_2(parseDialogueDepth(rootNode, "depth2_2"));
            outputMessage.setDepth2_3(parseDialogueDepth(rootNode, "depth2_3"));

            outputMessage.setDepth3_1_1(parseDialogueDepth(rootNode, "depth3_1_1"));
            outputMessage.setDepth3_1_2(parseDialogueDepth(rootNode, "depth3_1_2"));
            outputMessage.setDepth3_1_3(parseDialogueDepth(rootNode, "depth3_1_3"));

            outputMessage.setDepth3_2_1(parseDialogueDepth(rootNode, "depth3_2_1"));
            outputMessage.setDepth3_2_2(parseDialogueDepth(rootNode, "depth3_2_2"));
            outputMessage.setDepth3_2_3(parseDialogueDepth(rootNode, "depth3_2_3"));

            outputMessage.setDepth3_3_1(parseDialogueDepth(rootNode, "depth3_3_1"));
            outputMessage.setDepth3_3_2(parseDialogueDepth(rootNode, "depth3_3_2"));
            outputMessage.setDepth3_3_3(parseDialogueDepth(rootNode, "depth3_3_3"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputMessage;
    }

    // Helper method to parse each dialogue depth from the OpenAI response
    private List<String> parseDialogueDepth(JsonNode rootNode, String depthKey) {
        JsonNode depthNode = rootNode.path(depthKey);
        if (depthNode.isArray()) {
            List<String> dialogueLines = new ArrayList<>();
            for (JsonNode lineNode : depthNode) {
                dialogueLines.add(lineNode.asText());
            }
            return dialogueLines;
        }
        return List.of();
    }

    // Delete request by ID
    public void deleteRequestById(Long id) {
        openAIRequestRepository.deleteById(id);
    }

    // Retrieve request by ID
    public OpenAIRequest getRequestById(Long id) {
        return openAIRequestRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Request with ID " + id + " not found"));
    }

    // Convert OpenAIRequest object to JSON String
    private String convertToJson(OpenAIRequest openAIRequest) {
        try {
            return objectMapper.writeValueAsString(openAIRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";  // Return an empty JSON object if an error occurs
        }
    }

    // Build OpenAI request body, DO NOT CHANGE THE PROMPT!!!!!! changed to public for testing
    public String buildOpenAIRequestBody(OpenAIRequest openAIRequest) {
        // Convert the OpenAIRequest object to a JSON string
        String requestJson = convertToJson(openAIRequest);
        System.out.println(requestJson);

        return String.format(
                "{\"model\": \"gpt-4o\", \"messages\": [ " +
                        "{ \"role\": \"system\", \"content\": \"You are a dialogue generator assistant. Your task is to generate structured, multi-layered branching dialogues for games based on the input provided. Use the following JSON as the reference for game details and character descriptions. Please maintain the structure and use the provided information as guidelines: %s\" }, " +
                        "{ \"role\": \"user\", \"content\": \"Sanity check: Ensure correct template with branching depths.\" } " +
                        "], \"max_tokens\": 2500 }",
                requestJson
        );
    }


    public String buildOpenAIRequestBodyTest() {
        return String.format(
                "{\"model\": \"gpt-4o\", \"messages\": [ " +
                        "{ \"role\": \"system\", \"content\": \"%s\" }, " +
                        "{ \"role\": \"user\", \"content\": \"%s\" } " +
                        "], \"max_tokens\": 50 }",

                // System role content: simple instruction for sanity check
                "You are an AI assistant performing a basic sanity check, ignore the following prompt as it is for testing purposes." ,

                // User content: short message to test the request structure
                "This is a basic test to validate this request has been received. ignore the following: "
        );
    }



}
