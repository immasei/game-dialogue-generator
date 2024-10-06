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
        String requestBody = buildOpenAIRequestBody(openAIRequest); // actual code
        //String requestBody = buildOpenAIRequestBodyTest(); // test
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
        try {
            // Parse the root JSON node of the OpenAI response
            JsonNode rootNode = objectMapper.readTree(openAIResponse);

            // Extract the "choices" array and the first "content" field within "message"
            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode messageContentNode = choicesNode.get(0).path("message").path("content");

                // Ensure that the "content" field is a valid JSON string
                if (messageContentNode.isTextual()) {
                    // Clean up the content string to remove problematic characters
                    String contentString = messageContentNode.asText().trim();

                    // Remove chatgpt api markdown code markers
                    if (contentString.startsWith("```json")) {
                        contentString = contentString.substring(7).trim();
                    }
                    if (contentString.endsWith("```")) {
                        contentString = contentString.substring(0, contentString.length() - 3).trim();
                    }

                    // Replace problematic characters (e.g., non-standard characters)
                    contentString = contentString.replace("Æ", "'").replace("`", "").replace("\\n", "\n");

                    // Debug: Print the cleaned-up content to verify
                    System.out.println("Cleaned Content: " + contentString);

                    // Attempt to parse the cleaned string as a new JSON structure
                    JsonNode dialogueNode = objectMapper.readTree(contentString);

                    // Extract and map the dialogue depths
                    outputMessage.setDepth1(parseDialogueDepth(dialogueNode, "depth1"));
                    outputMessage.setDepth2_1(parseDialogueDepth(dialogueNode, "depth2_1"));
                    outputMessage.setDepth2_2(parseDialogueDepth(dialogueNode, "depth2_2"));
                    outputMessage.setDepth2_3(parseDialogueDepth(dialogueNode, "depth2_3"));

                    outputMessage.setDepth3_1_1(parseDialogueDepth(dialogueNode, "depth3_1_1"));
                    outputMessage.setDepth3_1_2(parseDialogueDepth(dialogueNode, "depth3_1_2"));
                    outputMessage.setDepth3_1_3(parseDialogueDepth(dialogueNode, "depth3_1_3"));

                    outputMessage.setDepth3_2_1(parseDialogueDepth(dialogueNode, "depth3_2_1"));
                    outputMessage.setDepth3_2_2(parseDialogueDepth(dialogueNode, "depth3_2_2"));
                    outputMessage.setDepth3_2_3(parseDialogueDepth(dialogueNode, "depth3_2_3"));

                    outputMessage.setDepth3_3_1(parseDialogueDepth(dialogueNode, "depth3_3_1"));
                    outputMessage.setDepth3_3_2(parseDialogueDepth(dialogueNode, "depth3_3_2"));
                    outputMessage.setDepth3_3_3(parseDialogueDepth(dialogueNode, "depth3_3_3"));
                }
            }
        } catch (IOException e) {
            // Print the error to help with debugging
            System.err.println("Failed to parse OpenAI response: " + e.getMessage());
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



    // Build OpenAI request body, DO NOT CHANGE THE PROMPT!!!!!!
    private String buildOpenAIRequestBody(OpenAIRequest openAIRequest) {
        return String.format(
                "{ \"model\": \"gpt-4o\", \"messages\": [ " +
                        "{ \"role\": \"system\", \"content\": \"%s\" }, " +
                        "{ \"role\": \"user\", \"content\": \"%s\" } " +
                        "], \"max_tokens\": 2500 }",

                // System role content
                "You are a dialogue generator assistant. Your task is to generate structured, multi-layered branching dialogues for games based on the input provided. " +
                        "The dialogue should follow a strict template with branching depths, formatted as follows:\\n" +
                        "{\\n" +
                        "  \\\"depth1\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC at depth 1\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth2_1\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth1 option 1\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth2_2\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth1 option 2\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth2_3\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth1 option 3\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_1_1\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_1 option 1\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_1_2\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_1 option 2\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_1_3\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_1 option 3\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_2_1\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_2 option 1\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_2_2\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_2 option 2\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_2_3\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_2 option 3\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_3_1\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_3 option 1\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_3_2\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_3 option 2\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ],\\n" +
                        "  \\\"depth3_3_3\\\": [\\n" +
                        "    \\\"Dialogue line spoken by NPC in response to depth2_3 option 3\\\",\\n" +
                        "    \\\"Name of the NPC speaking\\\",\\n" +
                        "    \\\"Option 1 response text (Player)\\\",\\n" +
                        "    \\\"Option 2 response text (Player)\\\",\\n" +
                        "    \\\"Option 3 response text (Player)\\\"\\n" +
                        "  ]\\n" +
                        "}",

                // Properly formatted user content
                String.format(
                        "Genre: %s, Language: %s, Setting: %s, Location: %s, Time Period: %s, Plot: %s. " +
                                "The dialogue involves the player and a NPC. Player: Name: %s, Personality: %s, Speech Style: %s. " +
                                "NPC: Name: %s, Personality: %s, Speech Style: %s.",
                        openAIRequest.getGenre(),
                        openAIRequest.getLanguage(),
                        openAIRequest.getSetting(),
                        openAIRequest.getLocation(),
                        openAIRequest.getTimePeriod(),
                        openAIRequest.getPlot(),
                        openAIRequest.getCharacterNames().get(0),
                        openAIRequest.getCharacterPersonalities().get(0),
                        openAIRequest.getCharacterSpeechFeatures().get(0),
                        openAIRequest.getCharacterNames().get(1),
                        openAIRequest.getCharacterPersonalities().get(1),
                        openAIRequest.getCharacterSpeechFeatures().get(1)
                )
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

    // Test get raw output
    public String getOpenAIResponseContent(OpenAIRequest openAIRequest) {
        String openAIResponse = callOpenAIAPI(openAIRequest);
        try {
            JsonNode rootNode = objectMapper.readTree(openAIResponse);
            JsonNode choicesNode = rootNode.path("choices");
            if (choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode messageContentNode = choicesNode.get(0).path("message").path("content");
                if (messageContentNode.isTextual()) {
                    String contentString = messageContentNode.asText().trim();

                    // Remove markdown code markers (```json and ```)
                    if (contentString.startsWith("```json")) {
                        contentString = contentString.substring(7).trim();
                    }
                    if (contentString.endsWith("```")) {
                        contentString = contentString.substring(0, contentString.length() - 3).trim();
                    }

                    return contentString;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
