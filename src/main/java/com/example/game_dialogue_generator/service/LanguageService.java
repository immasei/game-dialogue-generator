package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.model.Language;
import com.example.game_dialogue_generator.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public List<Language> getAllLanguages() {
        return (List<Language>) languageRepository.findAll();
    }
}
