package com.example.game_dialogue_generator.runner;

import com.example.game_dialogue_generator.enums.GenreEnum;
import com.example.game_dialogue_generator.model.Genre;
import com.example.game_dialogue_generator.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Initialise the Genre table with GenreEnum
 * TODO: if we update the GenreEnum, it will not update the entries in the Genre table
 */
@Component
public class GenreLoader implements ApplicationRunner {
    // https://bootify.io/next-steps/load-initial-data-in-spring-boot.html
    // https://dev.to/noelopez/spring-rest-working-with-enums-ma

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (genreRepository.count() != 0) {
            return;
        }

        for (int i = 0; i < GenreEnum.values().length; i++) {
            this.createGenre(GenreEnum.values()[i]);
        }
    }

    public void createGenre(GenreEnum genreEnum) {
        Genre genre = new Genre();
        genre.setName(genreEnum);
        genreRepository.save(genre);
    }
}
