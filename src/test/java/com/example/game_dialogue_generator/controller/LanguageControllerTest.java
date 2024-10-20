package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.enums.LanguageEnum;
import com.example.game_dialogue_generator.model.Genre;
import com.example.game_dialogue_generator.model.Language;
import com.example.game_dialogue_generator.service.LanguageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LanguageController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LanguageService languageService;

    @Test
    void testGetLanguages_Success() throws Exception {
        List<Language> languages = new ArrayList<>();
        Language language = new Language();
        language.setLanguageid(1);
        language.setName(LanguageEnum.ENGLISH);
        languages.add(language);
        when(languageService.getAllLanguages()).thenReturn(languages);

        mockMvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].name").value(language.getName().toString()))
                .andExpect(jsonPath("$.data[0].languageid").value(1));

        verify(languageService, times(1))
                .getAllLanguages();
    }

    @Test
    void testGetLanguages_Empty() throws Exception {
        List<Language> languages = new ArrayList<>();
        when(languageService.getAllLanguages()).thenReturn(languages);

        mockMvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());

        verify(languageService, times(1))
                .getAllLanguages();
    }

    @Test
    void testGetLanguages_Null() throws Exception {
        when(languageService.getAllLanguages()).thenReturn(null);

        mockMvc.perform(get("/languages"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(languageService, times(1))
                .getAllLanguages();
    }
}
