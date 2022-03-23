package ch.noseryoung.sbdemo01.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying @Transactional
    @Query("update User g set g.username = ?1, g.password = ?2 where g.UserId = ?3")
    User updateUser(String name,  String password, Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Modifying@Transactional
    @Query("update User u set u.authorized = true where u.email = ?1")
    void enableUser(String email);
}
