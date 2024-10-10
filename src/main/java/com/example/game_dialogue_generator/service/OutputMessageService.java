package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.model.OutputMessage;
import com.example.game_dialogue_generator.repository.OutputMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutputMessageService {

    @Autowired
    private OutputMessageRepository repository;

    // create
    public OutputMessage createOutputMessage(OutputMessage outputMessage) {
        return repository.save(outputMessage);
    }


    // get by id
    public Optional<OutputMessage> getOutputMessageById(Long id) {
        return repository.findById(id);
    }

    // update
    public OutputMessage updateOutputMessage(Long id, OutputMessage updatedOutputMessage) {
        Optional<OutputMessage> existingMessage = repository.findById(id);
        if (existingMessage.isPresent()) {
            updatedOutputMessage.setId(id);
            return repository.save(updatedOutputMessage);
        }
        return null; // placeholder, might add an exception in the future if i remember
    }

    // delete
    public void deleteOutputMessage(Long id) {
        repository.deleteById(id);
    }
}
