package ch.noseryoung.sbdemo01.genre;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class Genre {
    @Id
    @SequenceGenerator
            (
            name="genre_sequence",
            sequenceName = "genre_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    private Double popularity;
    private String description;

    public Genre(String name, Double popularity, String description) {
        this.name = name;
        this.popularity = popularity;
        this.description = description;
    }

    public Genre(){

    }

}
