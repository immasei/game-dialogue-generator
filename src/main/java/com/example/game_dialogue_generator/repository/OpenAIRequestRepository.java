package com.example.game_dialogue_generator.repository;

import com.example.game_dialogue_generator.model.OpenAIRequest;
import com.example.game_dialogue_generator.model.OutputMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for OpenAI requests.
 */
@Repository
public interface OpenAIRequestRepository extends CrudRepository<OpenAIRequest, Long> {
    @Override
    List<OpenAIRequest> findAll();

    Optional<OpenAIRequest> findOpenAIRequestByIdAndUserId(long id, int userid);

    List<OpenAIRequest> findOpenAIRequestByUserId(int userid);
}
