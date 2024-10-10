package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.model.OutputMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class PastebinService {

    @Value("${pastebin.api.url}")
    private String pastebinApiUrl;

    @Value("${pastebin.api.key}")
    private String pastebinApiKey;

    public String callPastebinAi(String outputMessage) {
        // Change to Json
//        String jsonText = null;
//        try {
//            ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
//            jsonText = objectWriter.writeValueAsString(outputMessage);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }

//        outputMessage = """
//                {
//                "asdfasdf": "asdfadsf"
//        }""";
        // Make api call
//        WebClient webClient = WebClient.builder()
//                .baseUrl(pastebinApiUrl)
//                .build();
//
//        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
//        bodyValues.add("api_dev_key", pastebinApiKey);
//        bodyValues.add("api_option", "paste");
//        bodyValues.add("api_paste_code", outputMessage);
//        bodyValues.add("api_paste_format", "json");
//
//        Mono<String> response = webClient.post()
//                .body(BodyInserters.fromFormData(bodyValues))
//                .retrieve()
//                .bodyToMono(String.class);
//
//        String responseString = response.block();
//
//        return responseString;
//        WebClient webClient = WebClient.create("https://pastebin.com/api/api_post.php");
//
//// Prepare data as a MultiValueMap
//        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add("api_dev_key", pastebinApiKey); // Replace with your actual key
//        formData.add("api_paste_code", "{\"testing\":\"test\"}"); // Your JSON data
//        formData.add("api_paste_format", "json");
//        formData.add("api_option", "paste");
//
//// Build the request with form data and appropriate content-type header
//        Mono<String> responseMono = webClient.post()
//                .body(BodyInserters.fromFormData(formData))
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .retrieve()
//                .bodyToMono(String.class);

//        return responseMono.block();
//        WebClient webClient = WebClient.create(this.pastebinApiUrl);
//        JSONObject jsonData = new JSONObject("{'testing':'test'}");
//
//        MultiValueMap<String, String> bodyValues = new LinkedMultiValueMap<>();
//        bodyValues.add("api_dev_key", pastebinApiKey);
//        bodyValues.add("api_option", "paste");
//        bodyValues.add("api_paste_code", "some text");
//        bodyValues.add("api_paste_format", "php");
//
//        Mono<String> responseMono = webClient.post()
//                .bodyValue("api_dev_key="+pastebinApiKey+"&api_option=paste&api_paste_code='some text'&api_paste_format=php")
//                .retrieve()
//                .bodyToMono(String.class);
//
//        return responseMono.block();

//        Trying to send the equivalent of this curl (which works)
//        curl -X POST -d 'api_dev_key=BBIaQuWXAzU8HFumpY3FBErVTKPytOfy' -d 'api_paste_code={"testing":"test"}' -d 'api_paste_format=json' -d 'api_option=paste' "https://pastebin.com/api/api_post.php"

        WebClient webClient = WebClient.create("https://pastebin.com/api/api_post.php");

        Mono<String> response = webClient.post()
                .bodyValue("api_dev_key=adsfadsfdsf&api_paste_code={'testing':'test'}&api_paste_format=json&api_option=paste")
                .retrieve()
                .bodyToMono(String.class);

        response.subscribe(System.out::println);

        return "response.toString();";
    }
}
