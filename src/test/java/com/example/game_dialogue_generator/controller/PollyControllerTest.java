package com.example.game_dialogue_generator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.game_dialogue_generator.service.PollyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PollyControllerTest {

    @InjectMocks
    private PollyController pollyController;

    @Mock
    private PollyService pollyService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pollyController).build();
    }

    @Test
    public void testSynthesizeSpeech() throws Exception {
        String mockAudio = "mock audio";
        InputStream audioStream = new ByteArrayInputStream(mockAudio.getBytes());

        // 模拟 PollyService 的行为
        when(pollyService.synthesizeSpeech(any(String.class))).thenReturn(audioStream);

        // 发送请求并验证结果
        mockMvc.perform(get("/synthesize")
                        .param("text", "Hello"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.mp3"))
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
    }
}
