package com.example.game_dialogue_generator.service;

import com.example.game_dialogue_generator.enums.GenreEnum;
import com.example.game_dialogue_generator.model.Genre;
import com.example.game_dialogue_generator.repository.GenreRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService;

    @Test
    void testGetAllGenres_success() {
        List<Genre> genres = new ArrayList<>();
        Genre genre1 = new Genre();
        genre1.setGenreid(1);
        genre1.setName(GenreEnum.ACTION);
        genres.add(genre1);

        when(genreRepository.findAll()).thenReturn(genres);

        List<Genre> result = genreService.getAllGenres();

        assertNotNull(result);
        assertArrayEquals(genres.toArray(), result.toArray());
        verify(genreRepository, times(1)).findAll();
    }
}
