package com.example.game_dialogue_generator.repository;

import com.example.game_dialogue_generator.dto.OutputMessageDTO;
import com.example.game_dialogue_generator.model.OutputMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OutputMessageRepository extends CrudRepository<OutputMessage, Long> {
    @Override
    List<OutputMessage> findAll();

    Optional<OutputMessage> findOutputMessageByIdAndUserId(long id, int userid);
    List<OutputMessage> findOutputMessageByUserId(int userid);
}
