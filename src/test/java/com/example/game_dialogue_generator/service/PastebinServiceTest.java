package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.model.OutputMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PastebinServiceTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private PastebinService pastebinService;

    @Value("${pastebin.api.url}")
    private String pastebinApiUrl;

    @Value("${pastebin.api.key}")
    private String pastebinApiKey;

   @Test
    void testCallPastebinAi() throws JsonProcessingException {
       OutputMessage outputMessage = new OutputMessage();
       String expected = "https://pastebin.com/mSEn3d5z";
       String expectedJsonText = "{\n" +
               "  \"id\" : null,\n" +
               "  \"userId\" : null,\n" +
               "  \"depth1\" : null,\n" +
               "  \"depth2_1\" : null,\n" +
               "  \"depth2_2\" : null,\n" +
               "  \"depth2_3\" : null,\n" +
               "  \"depth3_1_1\" : null,\n" +
               "  \"depth3_1_2\" : null,\n" +
               "  \"depth3_1_3\" : null,\n" +
               "  \"depth3_2_1\" : null,\n" +
               "  \"depth3_2_2\" : null,\n" +
               "  \"depth3_2_3\" : null,\n" +
               "  \"depth3_3_1\" : null,\n" +
               "  \"depth3_3_2\" : null,\n" +
               "  \"depth3_3_3\" : null\n" +
               "}";

       MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
       formData.add("api_dev_key", pastebinApiKey);
       formData.add("api_paste_code", expectedJsonText);
       formData.add("api_paste_format", "json");
       formData.add("api_option", "paste");

       Mockito.mockStatic(WebClient.class);
       when(WebClient.create(pastebinApiUrl)).thenReturn(webClient);
       when(webClient.post()).thenReturn(requestBodyUriSpec);
       when(requestBodyUriSpec.bodyValue(formData)).thenReturn(requestHeadersSpec);
       when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
       when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(expected));

       String result = pastebinService.callPastebinAi(outputMessage);

       assertEquals(expected, result);
   }

   @Test
   void testCallPastebinAi_JsonException() throws JsonProcessingException {
       ObjectWriter objectWriter = mock(ObjectWriter.class);
       when(objectWriter.withDefaultPrettyPrinter()).thenReturn(objectWriter);
       when(objectWriter.writeValueAsString(Mockito.any())).thenThrow(JsonProcessingException.class);

       try (MockedConstruction<ObjectMapper> mockedObjectMapper = Mockito.mockConstruction(ObjectMapper.class,
               (mock, context) -> {

           when(mock.writer()).thenReturn(objectWriter);
       })) {
           String result = pastebinService.callPastebinAi(new OutputMessage());

           assertTrue(result.contains("Bad API request"));
       }
   }
}
