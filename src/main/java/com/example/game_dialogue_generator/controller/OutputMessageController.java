package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.service.OutputMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * controller for managing OutputMessage
 *
 * it provides CRUD operations including:
 * - create
 * - retrieve all or by id.
 * - update by id
 * - delete by id
 *
 * Endpoints:
 * - POST /api/outputmessages: create
 * - GET /api/outputmessages: get all
 * - GET /api/outputmessages/{id}: get by id
 * - PUT /api/outputmessages/{id}: update by id
 * - DELETE /api/outputmessages/{id}: delete by id
 *
 * @see OutputMessage
 * @see OutputMessageService
 */
@RestController
@RequestMapping("/api/outputmessages")
public class OutputMessageController {

    @Autowired
    private OutputMessageService service;

    // create
    @PostMapping
    public ResponseEntity<OutputMessage> createOutputMessage(@RequestBody OutputMessage outputMessage) {
        OutputMessage createdOutputMessage = service.createOutputMessage(outputMessage);
        return ResponseEntity.ok(createdOutputMessage);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<OutputMessage> getOutputMessageById(@PathVariable Long id) {
        Optional<OutputMessage> outputMessage = service.getOutputMessageById(id);
        return outputMessage.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // update by id
    @PutMapping("/{id}")
    public ResponseEntity<OutputMessage> updateOutputMessage(@PathVariable Long id, @RequestBody OutputMessage outputMessage) {
        OutputMessage updatedOutputMessage = service.updateOutputMessage(id, outputMessage);
        if (updatedOutputMessage != null) {
            return ResponseEntity.ok(updatedOutputMessage);
        }
        return ResponseEntity.notFound().build();
    }

    // delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOutputMessage(@PathVariable Long id) {
        service.deleteOutputMessage(id);
        return ResponseEntity.ok("OutputMessage: " + id + " has been deleted successfully.");
    }

}
