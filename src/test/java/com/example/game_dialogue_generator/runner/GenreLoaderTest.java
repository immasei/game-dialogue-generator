package com.example.game_dialogue_generator.runner;

import com.example.game_dialogue_generator.enums.GenreEnum;
import com.example.game_dialogue_generator.model.Genre;
import com.example.game_dialogue_generator.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreLoaderTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreLoader genreLoader;

    @Test
    public void testRun() {
        doNothing().when(genreRepository).deleteAll();
        when(genreRepository.save(any(Genre.class)))
                .thenReturn(null);

        genreLoader.run(null);

        verify(genreRepository, times((1))).deleteAll();
        verify(genreRepository, times(GenreEnum.values().length))
                .save(any());
    }

    @Test
    public void testCreateGenre() {
        when(genreRepository.save(any(Genre.class))).thenReturn(null);

        genreLoader.createGenre(GenreEnum.ACTION);

        verify(genreRepository, times(1)).save(any());
    }
}
