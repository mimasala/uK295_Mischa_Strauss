package ch.noseryoung.sbdemo01.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public Genre getGenre(Long genreId) {
        return genreRepository.findById(genreId).orElseThrow(() -> new IllegalStateException("genre does not exist"));
    }

    public void addGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public void deleteGenre(Long genreID) {
        genreRepository.deleteById(genreID);
    }

    @Transactional
    public Genre updateGenre(Genre genre, Long genreID) {
        return genreRepository.updateGenre(genre.getName(), genre.getDescription(), genre.getPopularity(), genreID);
    }
}
