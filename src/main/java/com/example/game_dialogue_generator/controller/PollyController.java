package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.service.PollyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class PollyController {

    @Autowired
    private PollyService pollyService;

    public PollyController(PollyService pollyService) {
        this.pollyService = pollyService;
    }

    @GetMapping("/synthesize")
    public ResponseEntity<InputStreamResource> synthesizeSpeech(@RequestParam String text) {
        InputStream  audioStream = pollyService.synthesizeSpeech(text);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.mp3")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(audioStream));
    }
}
