package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.dto.OpenAIRequestDTO;
import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.OpenAIRequest;
import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.repository.OpenAIRequestRepository;
import com.example.game_dialogue_generator.repository.OutputMessageRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private ModelMapper modelMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<OpenAIRequestDTO> findOpenAIRequestByUserId(int userid) {
        return openAIRequestRepository.findOpenAIRequestByUserId(userid).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<OpenAIRequestDTO> findOpenAIRequestByIdAndUserId(long id, int userId) {
        return openAIRequestRepository.findOpenAIRequestByIdAndUserId(id, userId).map(this::convertToDTO);
    }

    public OpenAIRequestDTO updateOpenAIRequest(Long id, OpenAIRequestDTO updatedOpenAIRequestDTO) {
        Optional<OpenAIRequest> existingOpenAIRequest = openAIRequestRepository.findById(id);
        if (existingOpenAIRequest.isPresent()) {
            OpenAIRequest updatedOpenAIRequest = convertToModel(updatedOpenAIRequestDTO);
            updatedOpenAIRequest.setId(id);
            OpenAIRequest savedOpenAIRequest= openAIRequestRepository.save(updatedOpenAIRequest);
            return convertToDTO(savedOpenAIRequest);
        }
        return null; // placeholder, might add an exception in the future if I remember
    }

    // Create a new OpenAI request, call OpenAI, and map response to OutputMessageDTO
    public Long createOpenAIRequest(OpenAIRequestDTO openAIRequestDTO) {
        // Convert DTO to model
        OpenAIRequest openAIRequest = convertToModel(openAIRequestDTO);

        // Save request details to the database
        openAIRequestRepository.save(openAIRequest);

        // Call the OpenAI API for generating the dialogue
        String openAIResponse = callOpenAIAPI(openAIRequest);

        // Parse OpenAI response into OutputMessage
        OutputMessage outputMessage = parseOpenAIResponse(openAIResponse);
        outputMessage.setUserId(openAIRequest.getUserId());

        // Save and return the output message as DTO
        OutputMessage savedOutputMessage = outputMessageRepository.save(outputMessage);
//        return convertToDTO(savedOutputMessage);
        return savedOutputMessage.getId();
    }

    // API call to OpenAI
    private String callOpenAIAPI(OpenAIRequest openAIRequest) {
        WebClient webClient = WebClient.builder()
                .baseUrl(openAiApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .build();

        // Prepare the JSON request body
        String requestBody = buildOpenAIRequestBody(openAIRequest);
        System.out.println(requestBody);

        // Send the POST request to OpenAI and return the response body
        Mono<String> responseMono = webClient.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class);

        // Block to wait for response (suitable for synchronous applications)
        return responseMono.block();
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
                    contentString = contentString.replace("Æ", "'").replace("`", "").replace("\n", "\n");

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
    public OpenAIRequestDTO getRequestById(Long id) {
        OpenAIRequest request = openAIRequestRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Request with ID " + id + " not found"));
        return convertToDTO(request);
    }

    // Convert OpenAIRequest model to DTO
//    private OpenAIRequestDTO convertToDTO(OpenAIRequest openAIRequest) {
//        OpenAIRequestDTO dto = new OpenAIRequestDTO();
//        dto.setId(openAIRequest.getId());
//        dto.setGenre(openAIRequest.getGenre());
//        dto.setLanguage(openAIRequest.getLanguage());
//        dto.setSetting(openAIRequest.getSetting());
//        dto.setLocation(openAIRequest.getLocation());
//        dto.setTimePeriod(openAIRequest.getTimePeriod());
//        dto.setPlot(openAIRequest.getPlot());
//        dto.setCharacterNames(openAIRequest.getCharacterNames());
//        dto.setCharacterPersonalities(openAIRequest.getCharacterPersonalities());
//        dto.setCharacterSpeechFeatures(openAIRequest.getCharacterSpeechFeatures());
//        dto.setDepth(openAIRequest.getDepth());
//        dto.setWidth(openAIRequest.getWidth());
//        dto.setUserId(openAIRequest.getUserId());
//        return dto;
//    }

    // Convert DTO to OpenAIRequest model
//    private OpenAIRequest convertToModel(OpenAIRequestDTO dto) {
//        OpenAIRequest openAIRequest = new OpenAIRequest();
//        openAIRequest.setId(dto.getId());
//        openAIRequest.setGenre(dto.getGenre());
//        openAIRequest.setLanguage(dto.getLanguage());
//        openAIRequest.setSetting(dto.getSetting());
//        openAIRequest.setLocation(dto.getLocation());
//        openAIRequest.setTimePeriod(dto.getTimePeriod());
//        openAIRequest.setPlot(dto.getPlot());
//        openAIRequest.setCharacterNames(dto.getCharacterNames());
//        openAIRequest.setCharacterPersonalities(dto.getCharacterPersonalities());
//        openAIRequest.setCharacterSpeechFeatures(dto.getCharacterSpeechFeatures());
//        openAIRequest.setDepth(dto.getDepth());
//        openAIRequest.setWidth(dto.getWidth());
//        openAIRequest.setUserId(dto.getUserId());
//        return openAIRequest;
//    }
//
//    // Convert OutputMessage model to DTO
//    private OutputMessageDTO convertToDTO(OutputMessage outputMessage) {
//        OutputMessageDTO dto = new OutputMessageDTO();
//        dto.setId(outputMessage.getId());
//        dto.setDepth1(outputMessage.getDepth1());
//        dto.setDepth2_1(outputMessage.getDepth2_1());
//        dto.setDepth2_2(outputMessage.getDepth2_2());
//        dto.setDepth2_3(outputMessage.getDepth2_3());
//        dto.setDepth3_1_1(outputMessage.getDepth3_1_1());
//        dto.setDepth3_1_2(outputMessage.getDepth3_1_2());
//        dto.setDepth3_1_3(outputMessage.getDepth3_1_3());
//        dto.setDepth3_2_1(outputMessage.getDepth3_2_1());
//        dto.setDepth3_2_2(outputMessage.getDepth3_2_2());
//        dto.setDepth3_2_3(outputMessage.getDepth3_2_3());
//        dto.setDepth3_3_1(outputMessage.getDepth3_3_1());
//        dto.setDepth3_3_2(outputMessage.getDepth3_3_2());
//        dto.setDepth3_3_3(outputMessage.getDepth3_3_3());
//        dto.setUserId(outputMessage.getUserId());
//        return dto;
//    }

    public OpenAIRequestDTO convertToDTO(OpenAIRequest openAIRequest) {
        return modelMapper.map(openAIRequest, OpenAIRequestDTO.class);
    }

    public OpenAIRequest convertToModel(OpenAIRequestDTO openAIRequestDTO) {
        return modelMapper.map(openAIRequestDTO, OpenAIRequest.class);
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

    // Test get raw output
    public String getOpenAIResponseContent(OpenAIRequestDTO openAIRequestDTO) {
        OpenAIRequest openAIRequest = convertToModel(openAIRequestDTO);
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

    // Build OpenAI request body
    private String buildOpenAIRequestBody(OpenAIRequest openAIRequest) {
        return String.format(
                "{ \"model\": \"gpt-4o\", \"messages\": [ " +
                        "{ \"role\": \"system\", \"content\": \"%s\" }, " +
                        "{ \"role\": \"user\", \"content\": \"%s\" } " +
                        "], \"max_tokens\": 2500 }",

                // System role content with depth and width explanation
                String.format(
                        "You are a dialogue generator assistant. Your task is to generate structured, multi-layered branching dialogues for games based on the input provided. (Do not add additional tags or change the order of the output contents as the api output is parsed by a jsonparser for an enterprise application.) " +
                                "The dialogue should follow a strict template with branching depths and widths.\\n" +
                                "The depth parameter (n) indicates how many levels of conversation there are, with values ranging from 1 to 3, which determines the n of depthn_m_nm.\\n" +
                                "The width parameter (m) indicates how many response options are available at each level, also ranging from 1 to 3 which determines the m and nm (where nm only occurs in depth3_m_nm) of depthn_m_nm. Note: nm is not n*m it is determined by m as the highest option number.\\n" +
                                "The branching is represented by specific depth levels, as explained below:\\n" +
                                "\\n" +
                                "1. **depth1**: The initial dialogue line spoken by the NPC. This should be followed by width number of possible response options from the player, corresponding to the width parameter.\\n" +
                                "\\n" +
                                "2. **depth2_x**: Represents the NPC response to the player's choice from **depth1**. Here, 'x' stands for the option selected by the player (e.g., depth2_1 means the NPC is responding to the player's selection of Option 1 in **depth1**). " +
                                "Each **depth2_x** should have an NPC response, the NPC's name, and up to width number of new options for the player to respond, as determined by the width parameter.\\n" +
                                "\\n" +
                                "3. **depth3_x_x**: Represents the next level of the NPC's response based on the player's selection at **depth2_x**. " +
                                "The first 'x' refers to the player's choice in **depth1**, and the second 'x' refers to the player's choice in **depth2**. " +
                                "For example, **depth3_1_2** means the NPC is responding to the player's selection of Option 1 in **depth1** and then Option 2 in **depth2_1**. " +
                                "Each **depth3_x_x** should include the NPC's response, the NPC's name, and up to width number possible responses for the player, as defined by the width parameter.\\n" +
                                "\\n" +
                                "Please ensure that the structure of the dialogue is consistent with the given depth and width, providing appropriate branching for each level up to the specified parameters.\\n" +
                                "Use the format below to structure the dialogue - note if width < 3 then number of options you generate will be based on width, e.g. a width of 2 will mean only 2 options provided, the 3rd option is empty and therefore in the subsequent depth, there will only be 2 options corresponding to option 1 and 2 for depth 1, and so forth:\\n" +
                                "{\\n" +
                                "  \\\"depth1\\\": [\\n" +
                                "    \\\"Dialogue line spoken by NPC at depth 1\\\",\\n" +
                                "    \\\"Name of the NPC speaking\\\",\\n" +
                                "    \\\"Option 1 response text (Player)\\\",\\n" +
                                "    \\\"Option 2 response text (Player)\\\",\\n" +
                                "    \\\"Option 3 response text (Player)\\\"\\n" +
                                "  ],\\n" +
                                "  (similarly structured branches for subsequent depths up to depthx_x_x based on the given depth and width)\\n" +
                                "}"
                ),

                // Properly formatted user content
                String.format(
                        "Genre: %s, Language: %s, Setting: %s, Location: %s, Time Period: %s, Plot: %s. " +
                                "The dialogue involves the player and an NPC. Player: Name: %s, Personality: %s, Speech Style: %s. " +
                                "NPC: Name: %s, Personality: %s, Speech Style: %s. " +
                                "Generate dialogue with depth: %d and width: %d.",
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
                        openAIRequest.getCharacterSpeechFeatures().get(1),
                        openAIRequest.getDepth(),
                        openAIRequest.getWidth()
                )
        );
    }

}