package ch.noseryoung.sbdemo01.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying @Transactional
    @Query("update User g set g.username = ?1, g.password = ?2 where g.id = ?3")
    void updateUser(String name,  String password, Long id);
}