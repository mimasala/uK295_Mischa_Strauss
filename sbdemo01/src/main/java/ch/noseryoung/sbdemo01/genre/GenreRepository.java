package ch.noseryoung.sbdemo01.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Modifying@Transactional
    @Query("update Genre g set g.name = :name, g.description = :description, g.popularity = :popularity where g.id = :id")
    Genre updateGenre(
            String name,
            String description,
            Double popularity,
            Long id
    );
}
