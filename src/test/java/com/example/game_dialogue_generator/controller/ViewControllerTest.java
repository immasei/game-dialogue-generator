package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OpenAIRequestDTO;
import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.service.OpenAIRequestService;
import com.example.game_dialogue_generator.service.OutputMessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ViewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OutputMessageService outputMessageService;

    @MockBean
    private OpenAIRequestService openAIRequestService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ViewController viewController;
    
    @Test
    void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("auth"));
    }

    @Test
    void testHome() throws Exception {
        mockMvc.perform(get("/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void testDialogue_Success() throws Exception {
        User user = new User();
        user.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        OutputMessageDTO outputMessageDTO = new OutputMessageDTO();
        Optional<OutputMessageDTO> optOutputMessageDTO = Optional.of(outputMessageDTO);
        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(optOutputMessageDTO);

        mockMvc.perform(get("/dialogue/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("dialogue"))
                .andExpect(model().attribute("dialogue", optOutputMessageDTO.get()));
    }

    @Test
    void testDialogue_NoOutputMessage() throws Exception {
        User user = new User();
        user.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        List<OpenAIRequestDTO> openAIRequestDTOS = new ArrayList<>();

        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(Optional.empty());
        when(openAIRequestService.findOpenAIRequestByUserId(1))
                .thenReturn(openAIRequestDTOS);

        mockMvc.perform(get("/dialogue/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("archive"))
                .andExpect(model().attribute("dialogues", openAIRequestDTOS));
    }

    @Test
    void testPrompt_Success() throws Exception {
        User user = new User();
        user.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        OpenAIRequestDTO openAIRequestDTO = new OpenAIRequestDTO();
        openAIRequestDTO.setCharacterNames(new ArrayList<>(Arrays.asList("befuddle", "toddle")));
        openAIRequestDTO.setCharacterPersonalities(new ArrayList<>(Arrays.asList("happy", "sad")));
        openAIRequestDTO.setCharacterSpeechFeatures(new ArrayList<>(Arrays.asList("energetic", "verbose")));
        when(openAIRequestService.findOpenAIRequestByIdAndUserId(1,1))
                .thenReturn(Optional.of(openAIRequestDTO));

        mockMvc.perform(get("/prompt/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("prompt"))
                .andExpect(model().attribute("prompt", openAIRequestDTO));
    }

    @Test
    void testPrompt_NoOutputMessage() throws Exception {
        User user = new User();
        user.setUserid(1);
        when(authentication.getPrincipal()).thenReturn(user);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        List<OpenAIRequestDTO> openAIRequestDTOS = new ArrayList<>();

        when(outputMessageService.findOutputMessageByIdAndUserId(1, 1))
                .thenReturn(Optional.empty());
        when(openAIRequestService.findOpenAIRequestByUserId(1))
                .thenReturn(openAIRequestDTOS);

        mockMvc.perform(get("/prompt/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("archive"))
                .andExpect(model().attribute("dialogues", openAIRequestDTOS));
    }
}
