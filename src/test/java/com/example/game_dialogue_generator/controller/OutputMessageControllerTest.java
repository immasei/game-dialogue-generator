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

import java.util.ArrayList;
import java.util.List;
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
        responseDTO.setUserId(1);

        User user = new User();
        user.setUserid(1);

        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(outputMessageService.updateOutputMessage(any(), any()))
                .thenReturn(responseDTO);
        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/outputmessages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dialogue (OutputMessage): 1 has been updated successfully."));

        verify(outputMessageService, times(1)).updateOutputMessage(eq(1L), any(OutputMessageDTO.class));
    }

    @Test
    public void testUpdateOutputMessage_NotFound() throws Exception {
        User user = new User();
        user.setUserid(1);

        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        OutputMessageDTO responseDTO = new OutputMessageDTO();
        responseDTO.setId(1L);
        responseDTO.setUserId(1);

        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(Optional.of(responseDTO));
        when(outputMessageService.updateOutputMessage(eq(1L), any(OutputMessageDTO.class)))
                .thenReturn(null);

        mockMvc.perform(put("/api/outputmessages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userId\": 1}"))
                .andExpect(status().isNotFound());

        verify(outputMessageService, times(1)).updateOutputMessage(eq(1L), any(OutputMessageDTO.class));
    }

    @Test
    public void testUpdateOutputMessage_NoOutputMessageFound() throws Exception {
        User user = new User();
        user.setUserid(1);

        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/outputmessages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userId\": 1}"))
                .andExpect(status().isNotFound());

        verify(outputMessageService, times(1))
                .findOutputMessageByIdAndUserId(1, 1);
    }

    @Test
    public void testUpdateOutputMessage_IDNotMatchUserID() throws Exception {
        User user = new User();
        user.setUserid(2);

        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        OutputMessageDTO responseDTO = new OutputMessageDTO();
        responseDTO.setId(1L);
        responseDTO.setUserId(1);

        when(outputMessageService.findOutputMessageByIdAndUserId(1, 2))
                .thenReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/outputmessages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userId\": 1}"))
                .andExpect(status().isNotFound());

        verify(outputMessageService, times(1))
                .findOutputMessageByIdAndUserId(1, 2);
    }

    @Test
    public void testDeleteOutputMessage_Success() throws Exception {
        doNothing().when(outputMessageService).deleteOutputMessage(1L);

        mockMvc.perform(delete("/api/outputmessages/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Dialogue (OutputMessage): 1 has been deleted successfully."));

        verify(outputMessageService, times(1)).deleteOutputMessage(1L);
    }

    @Test
    public void testGetOutputMessageByIdandUserID_Success() throws Exception {
        Optional<OutputMessageDTO> opt = Optional.of(new OutputMessageDTO());
        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(opt);

        mockMvc.perform(get("/api/outputmessages/u1/1"))
                .andExpect(status().isOk());
        verify(outputMessageService, times(1))
                .findOutputMessageByIdAndUserId(1, 1);
    }

    @Test
    public void testGetOutputMessageByIdandUserID_NoOutputMessage() throws Exception {
        Optional<OutputMessageDTO> opt = Optional.empty();
        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(opt);

        mockMvc.perform(get("/api/outputmessages/u1/1"))
                .andExpect(status().isNotFound());
        verify(outputMessageService, times(1))
                .findOutputMessageByIdAndUserId(1, 1);
    }

    @Test
    public void testGetOutputMessageByUserID() throws Exception {
        List<OutputMessageDTO> outputMessageDTOS = new ArrayList<>();
        when(outputMessageService.findOutputMessageByUserId(1))
                .thenReturn(outputMessageDTOS);

        mockMvc.perform(get("/api/outputmessages/u1"))
                .andExpect(status().isOk());
        verify(outputMessageService, times(1))
                .findOutputMessageByUserId(1);
    }
}
