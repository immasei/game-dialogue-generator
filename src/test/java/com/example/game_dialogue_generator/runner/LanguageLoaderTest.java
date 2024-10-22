package com.example.game_dialogue_generator.runner;

import com.example.game_dialogue_generator.enums.LanguageEnum;
import com.example.game_dialogue_generator.model.Language;
import com.example.game_dialogue_generator.repository.LanguageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LanguageLoaderTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageLoader languageLoader;

    @Test
    public void testRun() {
        doNothing().when(languageRepository).deleteAll();
        when(languageRepository.save(any(Language.class)))
                .thenReturn(null);

        languageLoader.run(null);

        verify(languageRepository, times(1)).deleteAll();
        verify(languageRepository, times(LanguageEnum.values().length))
                .save(any(Language.class));
    }

    @Test
    public void testCreateLanguage() {
        when(languageRepository.save(any(Language.class))).thenReturn(null);

        languageLoader.createLanguage(LanguageEnum.ENGLISH);

        verify(languageRepository, times(1))
                .save(any(Language.class));
    }
}
