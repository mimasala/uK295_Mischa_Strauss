package ch.noseryoung.sbdemo01.scheduledTasks;

import ch.noseryoung.sbdemo01.register.token.ConfirmationToken;
import ch.noseryoung.sbdemo01.register.token.ConfirmationTokenRepository;
import ch.noseryoung.sbdemo01.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@EnableAsync
@Component@AllArgsConstructor
@EnableScheduling
public class DeleteUsers {


    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Async
    @Scheduled(fixedRate = 60000 )
    public void deleteUsers() {
        confirmationTokenRepository.getUnconfirmedTokens().forEach(confirmationToken -> {
            if (confirmationToken.getExpiresAt().isBefore(java.time.LocalDateTime.now())&&confirmationToken.getConfirmedAt()==null) {
                confirmationTokenRepository.deleteById(confirmationToken.getTokenId());
                userRepository.deleteById(confirmationToken.getUser().getUserId());
            }
        });
    }
}
