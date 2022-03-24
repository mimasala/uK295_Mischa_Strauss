package ch.noseryoung.sbdemo01.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/v1/genre/")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService){
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> findAll(){
        return genreService.getGenres();
    }

    @GetMapping(path = "{genreID}")
    public ResponseEntity<Optional<Genre>> findByIDController(@PathVariable Long genreID){
        return ResponseEntity.ok()
                .body(genreService.getGenre(genreID));
    }

    @PostMapping
    public void createNewGenre(@RequestBody Genre genre){
        genreService.addGenre(genre);
    }

    @DeleteMapping(path = "{genreID}")
    public void deleteGenre(@PathVariable Long genreID){
        genreService.deleteGenre(genreID);
    }

    @PutMapping(path = "{genreID}")
    public ResponseEntity<Genre> updateGenre(
            @PathVariable Long genreID,
            @RequestBody Genre genre){
        return ResponseEntity.ok().body(genreService.updateGenre(genre));
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> exception(IllegalStateException illegal){
        return ResponseEntity.status(404).body(illegal.getMessage());
    }
}
