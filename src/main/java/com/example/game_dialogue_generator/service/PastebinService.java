package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.model.OutputMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


@Service
public class PastebinService {

    @Value("${pastebin.api.url}")
    private String pastebinApiUrl;

    @Value("${pastebin.api.key}")
    private String pastebinApiKey;

    public String callPastebinAi(OutputMessage outputMessage) {
        // Change outputMessage to a string of JSON format
        String jsonText = null;
        try {
            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
            jsonText = objectWriter.writeValueAsString(outputMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Bad API request" + e.getMessage();
        }
        WebClient webClient = WebClient.create(pastebinApiUrl);


        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("api_dev_key", pastebinApiKey);
        formData.add("api_paste_code", jsonText);
        formData.add("api_paste_format", "json");
        formData.add("api_option", "paste");

        try {
            String result = webClient.post()
                    .bodyValue(formData)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return result;
        } catch (WebClientResponseException e) {
            return e.getResponseBodyAsString();
        }
    }

    public String callPastebinAiTest(String outputMessage) {
        // Change to Json
//        String jsonText = null;
//        try {
//            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            jsonText = objectWriter.writeValueAsString(outputMessage);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

        WebClient webClient = WebClient.create(pastebinApiUrl);

        String content = "{\"testing\":\"test\"}";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("api_dev_key", pastebinApiKey);
        formData.add("api_paste_code", content);
        formData.add("api_paste_format", "json");
        formData.add("api_option", "paste");

        String result = webClient.post()
                .bodyValue(formData)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return result;
    }
}
