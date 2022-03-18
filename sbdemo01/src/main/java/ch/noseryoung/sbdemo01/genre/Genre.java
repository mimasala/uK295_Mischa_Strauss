package ch.noseryoung.sbdemo01.genre;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
