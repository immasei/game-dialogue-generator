package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.enums.LanguageEnum;
import com.example.game_dialogue_generator.model.Language;
import com.example.game_dialogue_generator.repository.LanguageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LanguageServiceTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageService languageService;

    @Test
    void testGetAllLanguages_Success() {
        List<Language> languages = new ArrayList<>();
        Language language = new Language();
        language.setLanguageid(1);
        language.setName(LanguageEnum.ENGLISH);
        languages.add(language);
        when(languageRepository.findAll()).thenReturn(languages);

        List<Language> result = languageService.getAllLanguages();

        assertNotNull(result);
        assertArrayEquals(languages.toArray(), result.toArray());
        verify(languageRepository, times(1)).findAll();
    }
}
