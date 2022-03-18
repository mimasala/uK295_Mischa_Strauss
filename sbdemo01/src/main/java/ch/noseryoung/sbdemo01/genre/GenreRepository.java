package ch.noseryoung.sbdemo01.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Modifying @Transactional
    @Query("update Genre g set g.name = ?1, g.popularity = ?2, g.description = ?3 where g.id = ?4")
    void updateGenre(String name, Double popularity, String description, Long id);
}