package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.handler.ResponseHandler;
import com.example.game_dialogue_generator.model.Language;
import com.example.game_dialogue_generator.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping("/languages")
    public ResponseEntity<?> getAllLanguages() {
        // Returns all available languages
        List<Language> languages = languageService.getAllLanguages();
        return ResponseHandler.handle(HttpStatus.OK, "List of all languages", languages);
    }
}
