package ch.noseryoung.sbdemo01.genre;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GenreConfig {
    @Bean
    CommandLineRunner genreInit(GenreRepository repository) {
        return args -> {
            Genre one = new Genre(
                    "sci-fi",
                    12312.123,
                    "cool"
            );
            Genre two = new Genre(
                    "romance",
                    123.31,
                    "not cool"
            );
            repository.saveAll(List.of(one, two));
        };
    }
}
