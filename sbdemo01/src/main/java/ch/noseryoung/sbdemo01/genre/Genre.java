package ch.noseryoung.sbdemo01.genre;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private String name;
    @NotNull
    private Double popularity;
    @NotNull
    private String description;

    public Genre(String name, Double popularity, String description) {
        this.name = name;
        this.popularity = popularity;
        this.description = description;
    }

    public Genre(){

    }

}
