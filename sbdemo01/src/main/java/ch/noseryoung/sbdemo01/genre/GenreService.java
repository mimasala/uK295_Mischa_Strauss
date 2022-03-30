package ch.noseryoung.sbdemo01.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @Transactional
    public void deleteGenre(Long genreID) {
        genreRepository.delete(genreRepository.findById(genreID).orElseThrow(()->new IllegalStateException("genre with this ID does not exist")));
    }

    @Transactional
    public void updateGenre(Genre genre, Long genreID) {
        genreRepository.updateGenre(genre.getName(), genre.getDescription(), genre.getPopularity(), genreID);
    }

}
