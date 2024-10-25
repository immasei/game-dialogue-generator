package com.example.game_dialogue_generator.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.services.polly.PollyClient;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechRequest;
import software.amazon.awssdk.services.polly.model.SynthesizeSpeechResponse;
import software.amazon.awssdk.core.ResponseInputStream;

import java.io.InputStream;

public class PollyServiceTest {

    @Mock
    private PollyClient mockPollyClient;  // 模拟的 PollyClient

    @InjectMocks
    private PollyService pollyService;  // 注入模拟对象到 PollyService

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockPollyClient = mock(PollyClient.class);
        pollyService = new PollyService(mockPollyClient);
    }

    @Test
    public void testSynthesizeSpeech() throws Exception {
        // 模拟 ResponseInputStream 的返回
        ResponseInputStream<SynthesizeSpeechResponse> mockResponseInputStream =
                mock(ResponseInputStream.class);

        // 设置模拟行为
        when(mockPollyClient.synthesizeSpeech(any(SynthesizeSpeechRequest.class)))
                .thenReturn(mockResponseInputStream);

        // 调用被测试的方法
        InputStream result = pollyService.synthesizeSpeech("Hello");

        // 验证结果不为空
        assertNotNull(result);

        // 验证 PollyClient 的 synthesizeSpeech 方法被调用了一次
        verify(mockPollyClient, times(1)).synthesizeSpeech(any(SynthesizeSpeechRequest.class));
    }
}
