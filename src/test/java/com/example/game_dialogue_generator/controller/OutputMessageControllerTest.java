package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.OutputMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class OutputMessageControllerTest {

    @Mock
    private OutputMessageService outputMessageService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private OutputMessageController outputMessageController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(outputMessageController).build();
    }

    @Test
    public void testCreateOutputMessage_Success() throws Exception {
        OutputMessageDTO requestDTO = new OutputMessageDTO();
        requestDTO.setId(1L);
        // Add more properties to requestDTO as needed

        OutputMessageDTO responseDTO = new OutputMessageDTO();
        responseDTO.setId(1L);

        when(outputMessageService.createOutputMessage(any(OutputMessageDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/outputmessages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(outputMessageService, times(1)).createOutputMessage(any(OutputMessageDTO.class));
    }

    @Test
    public void testGetOutputMessageById_Success() throws Exception {
        OutputMessageDTO responseDTO = new OutputMessageDTO();
        responseDTO.setId(1L);

        when(outputMessageService.getOutputMessageById(1L)).thenReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/outputmessages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(outputMessageService, times(1)).getOutputMessageById(1L);
    }

    @Test
    public void testGetOutputMessageById_NotFound() throws Exception {
        when(outputMessageService.getOutputMessageById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/outputmessages/1"))
                .andExpect(status().isNotFound());

        verify(outputMessageService, times(1)).getOutputMessageById(1L);
    }

    @Test
    public void testUpdateOutputMessage_Success() throws Exception {
        OutputMessageDTO requestDTO = new OutputMessageDTO();
        requestDTO.setId(1L);
        // Add more properties to requestDTO as needed

        OutputMessageDTO responseDTO = new OutputMessageDTO();
        responseDTO.setId(1L);

        User user = new User();
        user.setUserid(1);

        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(outputMessageService.updateOutputMessage(any(), any()))
                .thenReturn(responseDTO);


        mockMvc.perform(put("/api/outputmessages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dialogue (OutputMessage): 1 has been updated successfully."));

        verify(outputMessageService, times(1)).updateOutputMessage(eq(1L), any(OutputMessageDTO.class));
    }

    @Test
    public void testUpdateOutputMessage_NotFound() throws Exception {
        when(outputMessageService.updateOutputMessage(eq(1L), any(OutputMessageDTO.class))).thenReturn(null);

        mockMvc.perform(put("/api/outputmessages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1}"))
                .andExpect(status().isNotFound());

        verify(outputMessageService, times(1)).updateOutputMessage(eq(1L), any(OutputMessageDTO.class));
    }

    @Test
    public void testDeleteOutputMessage_Success() throws Exception {
        doNothing().when(outputMessageService).deleteOutputMessage(1L);

        mockMvc.perform(delete("/api/outputmessages/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dialogue (OutputMessage): 1 has been deleted successfully."));

        verify(outputMessageService, times(1)).deleteOutputMessage(1L);
    }
}
