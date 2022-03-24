package ch.noseryoung.sbdemo01.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenre(Long genreId) {
        return genreRepository.findById(genreId);
    }

    public void addGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public void deleteGenre(Long genreID) {
        genreRepository.deleteById(genreID);
    }

    @Transactional
    public Genre updateGenre(Genre genre) {
        return genreRepository.save(genre);
    }
}
