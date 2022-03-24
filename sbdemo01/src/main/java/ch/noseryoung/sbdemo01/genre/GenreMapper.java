package ch.noseryoung.sbdemo01.genre;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GenreMapper {
    GenreDto genreToGenreDto(Genre genre);

    Genre genreDtoToGenre(GenreDto genreDto);
}
