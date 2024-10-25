package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.service.OpenAIRequestService;
import com.example.game_dialogue_generator.service.OutputMessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

    @InjectMocks
    private ViewController viewController;
    
    @Test
    void testIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("auth"));
    }
}
