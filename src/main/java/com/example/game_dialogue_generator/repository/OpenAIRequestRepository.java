package com.example.game_dialogue_generator.repository;

import com.example.game_dialogue_generator.model.OpenAIRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for OpenAI requests.
 */
@Repository
public interface OpenAIRequestRepository extends CrudRepository<OpenAIRequest, Long> {}