package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.dto.GenreDTO;
import com.example.game_dialogue_generator.model.Genre;
import com.example.game_dialogue_generator.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {
    // Logic for genre. Used by controller. Calls repository.

    @Autowired
    private GenreRepository genreRepository;

    public List<GenreDTO> getAllGenres() {
        List<Genre> genres = (List<Genre>) genreRepository.findAll();
        return convertToDTO(genres);
    }

    public List<GenreDTO> convertToDTO(List<Genre> genres) {
        List<GenreDTO> genresDTO = new ArrayList<>();
        for (Genre genre : genres) {
            GenreDTO dto = new GenreDTO(genre.getName());
            genresDTO.add(dto);
        }
        return genresDTO;
    }
}
