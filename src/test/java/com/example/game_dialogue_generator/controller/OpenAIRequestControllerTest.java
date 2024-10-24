package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OpenAIRequestDTO;
import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.service.OpenAIRequestService;
import com.example.game_dialogue_generator.service.OutputMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class OpenAIRequestControllerTest {

    @Mock
    private OpenAIRequestService openAIRequestService;

    @Mock
    private OutputMessageService outputMessageService;

    @InjectMocks
    private OpenAIRequestController openAIRequestController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(openAIRequestController).build();
    }

    @Test
    public void testCreateOpenAIRequest_Success() throws Exception {
        OpenAIRequestDTO requestDTO = new OpenAIRequestDTO();
        requestDTO.setDepth(2);
        requestDTO.setWidth(2);

        OutputMessageDTO responseDTO = new OutputMessageDTO();
        responseDTO.setId(1L);

        when(openAIRequestService.createOpenAIRequest(any(OpenAIRequestDTO.class))).thenReturn(responseDTO.getId());

        mockMvc.perform(post("/api/openai")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"depth\": 2, \"width\": 2}"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(1L)));
//                .andExpect(content().string().value(1L));

        verify(openAIRequestService, times(1)).createOpenAIRequest(any(OpenAIRequestDTO.class));
    }

    @Test
    public void testCreateOpenAIRequest_BadRequest_DepthOutOfRange() throws Exception {
        mockMvc.perform(post("/api/openai")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"depth\": 4, \"width\": 2}"))
                .andExpect(status().isBadRequest());

        verify(openAIRequestService, never()).createOpenAIRequest(any(OpenAIRequestDTO.class));
    }

    @Test
    public void testCreateOpenAIRequest_BadRequest_WidthOutOfRange() throws Exception {
        mockMvc.perform(post("/api/openai")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"depth\": 2, \"width\": 5}"))
                .andExpect(status().isBadRequest());

        verify(openAIRequestService, never()).createOpenAIRequest(any(OpenAIRequestDTO.class));
    }


    @Test
    public void testGetRequestById_Success() throws Exception {
        OpenAIRequestDTO responseDTO = new OpenAIRequestDTO();
        responseDTO.setId(1L);

        when(openAIRequestService.getRequestById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/openai/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(openAIRequestService, times(1)).getRequestById(1L);
    }

    @Test
    public void testGetRequestById_NotFound() throws Exception {
        when(openAIRequestService.getRequestById(1L)).thenThrow(new NoSuchElementException("Request not found"));

        mockMvc.perform(get("/api/openai/1"))
                .andExpect(status().isNotFound());

        verify(openAIRequestService, times(1)).getRequestById(1L);
    }

    @Test
    public void testDeleteRequestById_Success() throws Exception {
        doNothing().when(openAIRequestService).deleteRequestById(1L);

        mockMvc.perform(delete("/api/openai/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Request with ID 1 has been deleted successfully."));

        verify(openAIRequestService, times(1)).deleteRequestById(1L);
    }
}
