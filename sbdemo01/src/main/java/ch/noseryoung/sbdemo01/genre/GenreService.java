package ch.noseryoung.sbdemo01.genre;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Genre getGenre(Long genreId) {
        return genreRepository.getById(genreId);
    }

    public void addGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public void deleteGenre(Long genreID) {
        genreRepository.deleteById(genreID);
    }

    @Transactional
    public void updateGenre(Long genreID, String name, String description, Double popularity) {
        genreRepository.updateGenre(name, popularity, description, genreID);
    }
}
