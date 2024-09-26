package com.example.game_dialogue_generator.repository;

import com.example.game_dialogue_generator.model.OutputMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutputMessageRepository extends CrudRepository<OutputMessage, Long> {
}