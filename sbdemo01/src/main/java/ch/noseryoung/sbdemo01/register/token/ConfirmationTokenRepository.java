package ch.noseryoung.sbdemo01.register.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Modifying@Transactional
    @Query("update ConfirmationToken t set t.confirmedAt = ?1 where t.token = ?2")
    void updateConfirmedAt(LocalDateTime confirmed_at, String token);

    @Query("select t from ConfirmationToken t where t.confirmedAt is null")
    List<ConfirmationToken> getUnconfirmedTokens();
}
