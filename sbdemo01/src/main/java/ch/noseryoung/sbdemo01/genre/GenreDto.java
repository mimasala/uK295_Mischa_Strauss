package ch.noseryoung.sbdemo01.genre;

import lombok.Data;

import java.io.Serializable;

@Data
public class GenreDto implements Serializable {
    private final Long id;
    private final String name;
    private final Double popularity;
    private final String description;
}
