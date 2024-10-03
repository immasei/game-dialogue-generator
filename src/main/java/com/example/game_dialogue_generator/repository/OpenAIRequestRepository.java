package com.example.game_dialogue_generator.repository;

import com.example.game_dialogue_generator.model.OpenAIRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * repo interface for OpenAI req
 */
@Repository
public interface OpenAIRequestRepository extends CrudRepository<OpenAIRequest, Long> {
}
