package com.example.game_dialogue_generator.repository;

import com.example.game_dialogue_generator.model.Background;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundRepository extends CrudRepository<Background, Long> {
}
