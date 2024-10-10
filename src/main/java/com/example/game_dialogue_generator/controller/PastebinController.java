package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.service.PastebinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Creates a paste in Pastebin containing the generated dialogue and returns a
 * url link to access it.
 */
@RestController
@RequestMapping("/api/pastebin")
public class PastebinController {

    @Autowired
    private PastebinService pastebinService;

    // Creates a paste
    @GetMapping
    public ResponseEntity<String> getPastebin(@RequestBody String outputMessage) {
        String response = pastebinService.callPastebinAi(outputMessage);
        return ResponseEntity.ok(response);
//        return ResponseEntity.ok("good");
    }
}
