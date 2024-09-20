package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.handler.ResponseHandler;
import com.example.game_dialogue_generator.model.Genre;
import com.example.game_dialogue_generator.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GenreController {
    // Gets list of genres

    @Autowired
    private GenreService genreService;

    @GetMapping("/genres")
    public ResponseEntity<?> getAllGenres() {
        // Returns all available genres
        List<Genre> genres = genreService.getAllGenres();
        return ResponseHandler.handle(HttpStatus.OK, "List of all genres", genres);
    }
}
