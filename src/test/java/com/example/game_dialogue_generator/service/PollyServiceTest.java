package com.example.game_dialogue_generator.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.amazonaws.services.polly.AmazonPolly;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
//import software.amazon.awssdk.services.polly.PollyClient;
//import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
//import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
//import software.amazon.awssdk.core.ResponseInputStream;

import java.io.InputStream;

@ExtendWith(MockitoExtension.class)
public class PollyServiceTest {

    @Mock
    private AmazonPolly mockPollyClient;  // 模拟的 PollyClient

    @InjectMocks
    private PollyService pollyService;  // 注入模拟对象到 PollyService

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        mockPollyClient = mock(PollyClient.class);
//        pollyService = new PollyService(mockPollyClient);
//    }

    @Test
    public void testSynthesizeSpeech() throws Exception {
        SynthesizeSpeechResult mockResult = mock(SynthesizeSpeechResult.class);
        InputStream mockInputStream = mock(InputStream.class);

        when(mockPollyClient.synthesizeSpeech(any(SynthesizeSpeechRequest.class)))
                .thenReturn(mockResult);
        when(mockResult.getAudioStream()).thenReturn(mockInputStream);

        InputStream result = pollyService.synthesizeSpeech("Hello");
        assertNotNull(result);

        // PollyClient & synthesizeSpeech
        verify(mockPollyClient, times(1)).synthesizeSpeech(any(SynthesizeSpeechRequest.class));
    }
}