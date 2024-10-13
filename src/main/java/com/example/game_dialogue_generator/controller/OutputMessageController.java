package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.service.OutputMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller for managing OutputMessage.
 *
 * It provides CRUD operations including:
 * - create
 * - retrieve all or by id
 * - update by id
 * - delete by id
 *
 * Endpoints:
 * - POST /api/outputmessages: create
 * - GET /api/outputmessages/{id}: get by id
 * - PUT /api/outputmessages/{id}: update by id
 * - DELETE /api/outputmessages/{id}: delete by id
 *
 * @see OutputMessageDTO
 * @see OutputMessageService
 */
@RestController
@RequestMapping("/api/outputmessages")
public class OutputMessageController {

    @Autowired
    private OutputMessageService service;

    // Create a new OutputMessage
    @PostMapping
    public ResponseEntity<OutputMessageDTO> createOutputMessage(@RequestBody OutputMessageDTO outputMessageDTO) {
        OutputMessageDTO createdOutputMessage = service.createOutputMessage(outputMessageDTO);
        return ResponseEntity.ok(createdOutputMessage);
    }

    // Get OutputMessage by ID
    @GetMapping("/{id}")
    public ResponseEntity<OutputMessageDTO> getOutputMessageById(@PathVariable Long id) {
        Optional<OutputMessageDTO> outputMessageDTO = service.getOutputMessageById(id);
        return outputMessageDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update OutputMessage by ID
    @PutMapping("/{id}")
    public ResponseEntity<OutputMessageDTO> updateOutputMessage(@PathVariable Long id, @RequestBody OutputMessageDTO outputMessageDTO) {
        OutputMessageDTO updatedOutputMessage = service.updateOutputMessage(id, outputMessageDTO);
        if (updatedOutputMessage != null) {
            return ResponseEntity.ok(updatedOutputMessage);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete OutputMessage by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOutputMessage(@PathVariable Long id) {
        service.deleteOutputMessage(id);
        return ResponseEntity.ok("OutputMessage: " + id + " has been deleted successfully.");
    }
}
