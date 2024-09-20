package com.example.game_dialogue_generator.repository;

import com.example.game_dialogue_generator.model.Language;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends CrudRepository<Language, Integer> {
}
