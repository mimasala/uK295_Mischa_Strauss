package ch.noseryoung.sbdemo01.genre;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(path = "api/v1/genre/")
@AllArgsConstructor
public class GenreController {

    private final GenreService genreService;

    private final GenreMapper genreMapper;



    @GetMapping
    @Operation(summary = "lists all genres")
    public List<Genre> findAll(){
        return genreService.getGenres();
    }

    @Operation(summary = "gets genre by id")
    @GetMapping(path = "{genreID}")
    public ResponseEntity<GenreDto> findByIDController(@PathVariable Long genreID){
        return ResponseEntity.ok()
                .body(genreMapper.genreToGenreDto(genreService.getGenre(genreID)));
    }

    @PostMapping
    @Operation(summary = "creates new genre")
    public void createNewGenre(@Valid @RequestBody GenreDto genre){
        genreService.addGenre(genreMapper.genreDtoToGenre(genre));
    }

    @DeleteMapping(path = "{genreID}")
    @Operation(summary = "deletes genre by id")
    public void deleteGenre(@PathVariable Long genreID){
        genreService.deleteGenre(genreID);
    }

    @PutMapping(path = "{genreID}")
    @Operation(summary = "updates a genre value by id")
    public void updateGenre(
            @PathVariable Long genreID,
            @Valid @RequestBody GenreDto genre){
        genreService.updateGenre(genreMapper.genreDtoToGenre(genre), genreID);
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> exception(IllegalStateException illegal){
        return ResponseEntity.status(400).body(illegal.getMessage());
    }
}
