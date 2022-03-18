package ch.noseryoung.sbdemo01.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "api/v1/genre")
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
    public ResponseEntity<Genre> findByIDController(@PathVariable Long genreID){
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
    public void updateGenre(
            @PathVariable Long genreID,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Double popularity){
        genreService.updateGenre(genreID,name,description,popularity);
    }

}