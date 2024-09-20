package com.example.game_dialogue_generator.repository;

import com.example.game_dialogue_generator.model.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Integer> {
    // Get access to + modify genre repository
}
