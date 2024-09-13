package com.example.game_dialogue_generator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.game_dialogue_generator.model.User;

import java.util.Optional;

// crud
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}