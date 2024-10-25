package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.OutputMessageService;
import com.example.game_dialogue_generator.service.PastebinService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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
    private OutputMessageService outputMessageService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

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

    @Test
    void testExportAsPastebin_Success() throws Exception {
        User user = new User();
        user.setUserid(1);
        OutputMessage outputMessage = new OutputMessage();
        OutputMessageDTO outputMessageDTO = new OutputMessageDTO();
        Optional<OutputMessageDTO> optionalOutputMessage = Optional.of(outputMessageDTO);

        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(optionalOutputMessage);
        when(outputMessageService.convertToModel(outputMessageDTO))
                .thenReturn(outputMessage);
        when(pastebinService.callPastebinApi(any(OutputMessage.class)))
                .thenReturn("good");

        mockMvc.perform(get("/api/pastebin/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("good"));
        verify(outputMessageService, times(1))
                .findOutputMessageByIdAndUserId(1, 1);
        verify(outputMessageService, times(1))
                .convertToModel(outputMessageDTO);
    }

    @Test
    void testExportAsPastebin_NoOutputMessage() throws Exception {
        User user = new User();
        user.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        Optional<OutputMessageDTO> optionalOutputMessage = Optional.empty();
        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(optionalOutputMessage);

        mockMvc.perform(get("/api/pastebin/1"))
                .andExpect(status().isNotFound());
        verify(outputMessageService, times(1))
                .findOutputMessageByIdAndUserId(1,1);
    }
}
