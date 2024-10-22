package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.dto.LanguageDTO;
import com.example.game_dialogue_generator.model.Language;
import com.example.game_dialogue_generator.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public List<LanguageDTO> getAllLanguages() {
        List<Language> languages = (List<Language>) languageRepository.findAll();
        return convertToDTO(languages);
    }

    public List<LanguageDTO> convertToDTO(List<Language> languages) {
        List<LanguageDTO> languageDTOS = new ArrayList<>();
        for (Language language : languages) {
            LanguageDTO dto = new LanguageDTO(language.getName());
            languageDTOS.add(dto);
        }
        return languageDTOS;
    }
}
