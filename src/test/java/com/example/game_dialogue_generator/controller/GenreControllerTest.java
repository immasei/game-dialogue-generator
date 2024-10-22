package com.example.game_dialogue_generator.controller;

import com.example.game_dialogue_generator.dto.GenreDTO;
import com.example.game_dialogue_generator.enums.GenreEnum;
import com.example.game_dialogue_generator.model.Genre;
import com.example.game_dialogue_generator.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
@AutoConfigureMockMvc(addFilters = false)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    GenreService genreService;

    @Test
    void testGetGenres_Success() throws Exception {
        List<GenreDTO> genres = new ArrayList<>();
        GenreDTO genre = new GenreDTO(GenreEnum.ACTION);
        genres.add(genre);
        when(genreService.getAllGenres()).thenReturn(genres);

        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].name").value(genre.getName().toString()));
//                .andExpect(jsonPath("$.data[0].genreid").value(1));

        verify(genreService, times(1))
                .getAllGenres();
    }

    @Test
    void testGetGenres_Empty() throws Exception {
        List<GenreDTO> genres = new ArrayList<>();
        when(genreService.getAllGenres()).thenReturn(genres);

        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());

        verify(genreService, times(1))
                .getAllGenres();
    }

    @Test
    void testGetGenre_Null() throws Exception {
        when(genreService.getAllGenres()).thenReturn(null);

        mockMvc.perform(get("/genres"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(genreService, times(1))
                .getAllGenres();
    }
}
