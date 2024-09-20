package com.example.game_dialogue_generator.runner;

import com.example.game_dialogue_generator.enums.LanguageEnum;
import com.example.game_dialogue_generator.model.Language;
import com.example.game_dialogue_generator.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LanguageLoader implements ApplicationRunner {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (languageRepository.count() != 0) {
            return;
        }

        for (int i = 0; i < LanguageEnum.values().length; i++) {
            this.createLanguage(LanguageEnum.values()[i]);
        }
    }

    public void createLanguage(LanguageEnum languageEnum) {
        Language language = new Language();
        language.setName(languageEnum);
        languageRepository.save(language);
    }
}
