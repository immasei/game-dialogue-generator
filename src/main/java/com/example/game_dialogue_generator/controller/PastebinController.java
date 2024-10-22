package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.model.User;
import com.example.game_dialogue_generator.repository.OutputMessageRepository;
import com.example.game_dialogue_generator.service.OutputMessageService;
import com.example.game_dialogue_generator.service.PastebinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Creates a paste in Pastebin containing the generated dialogue and returns a
 * url link to access it.
 */
@RestController
@RequestMapping("/api/pastebin")
public class PastebinController {

    @Autowired
    private PastebinService pastebinService;

    @Autowired
    private OutputMessageService outputMessageService;

    @GetMapping("/{id}")
    public ResponseEntity<String> exportAsPastebin(@PathVariable Long id) {
        User principle = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userid = principle.getUserid();

        Optional<OutputMessageDTO> outputMessageDTO = outputMessageService.findOutputMessageByIdAndUserId(id, userid);
        if (outputMessageDTO.isEmpty()) ResponseEntity.notFound().build();

        OutputMessage outputMessage = outputMessageService.convertToModel(outputMessageDTO.get());

        return getPastebin(outputMessage);
    }

    @GetMapping
    public ResponseEntity<String> getPastebin(@RequestBody OutputMessage outputMessage) {
        String response = pastebinService.callPastebinAi(outputMessage);

        if (response.contains("Bad API request")) {
            return ResponseEntity.badRequest().body(response);
        } else {
            return ResponseEntity.ok(response);
        }
    }

    // Creates a paste
    @GetMapping("/test")
    public ResponseEntity<String> getPastebinTest(@RequestBody String outputMessage) {
        String response = pastebinService.callPastebinAiTest(outputMessage);
        return ResponseEntity.ok(response);
    }
}
