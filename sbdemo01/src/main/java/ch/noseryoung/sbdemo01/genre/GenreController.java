package ch.noseryoung.sbdemo01.genre;

import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "lists all genres")
    public List<Genre> findAll(){
        return genreService.getGenres();
    }

    @Operation(summary = "gets genre by id")
    @GetMapping(path = "{genreID}")
    public ResponseEntity<Optional<Genre>> findByIDController(@PathVariable Long genreID){
        return ResponseEntity.ok()
                .body(genreService.getGenre(genreID));
    }

    @PostMapping
    @Operation(summary = "creates new genre")
    public void createNewGenre(@RequestBody Genre genre){
        genreService.addGenre(genre);
    }

    @DeleteMapping(path = "{genreID}")
    @Operation(summary = "deletes genre by id")
    public void deleteGenre(@PathVariable Long genreID){
        genreService.deleteGenre(genreID);
    }

    @PutMapping(path = "{genreID}")
    @Operation(summary = "updates a genre value by id")
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
