package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.model.Genre;
import com.example.game_dialogue_generator.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    // Logic for genre. Used by controller. Calls repository.

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return (List<Genre>) genreRepository.findAll();
    }
}
