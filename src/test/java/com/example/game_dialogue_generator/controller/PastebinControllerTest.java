package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.service.PastebinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PastebinController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PastebinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PastebinService pastebinService;

    @Test
    void testGetPastebin_Success() throws Exception {
        String url = "https://pastebin.com/mSEn3d5z";
        OutputMessage outputMessage = new OutputMessage();
        when(pastebinService.callPastebinApi(any(OutputMessage.class)))
                .thenReturn(url);

        mockMvc.perform(get("/api/pastebin")
                .content(new ObjectMapper().writeValueAsString(outputMessage))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(url));
        verify(pastebinService, times(1))
                .callPastebinApi(any(OutputMessage.class));
    }

    @Test
    void testGetPastebin_BadRequest() throws Exception {
        String url = "Bad API request something happened";
        OutputMessage outputMessage = new OutputMessage();
        when(pastebinService.callPastebinApi(any(OutputMessage.class)))
                .thenReturn(url);

        mockMvc.perform(get("/api/pastebin")
                .content(new ObjectMapper().writeValueAsString(outputMessage))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(url));
        verify(pastebinService, times(1))
                .callPastebinApi(any(OutputMessage.class));
    }
}
