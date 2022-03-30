package ch.noseryoung.sbdemo01.scheduledTasks;

import ch.noseryoung.sbdemo01.register.token.ConfirmationToken;
import ch.noseryoung.sbdemo01.register.token.ConfirmationTokenRepository;
import ch.noseryoung.sbdemo01.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAsync
@Component@AllArgsConstructor
public class DeleteUsers {


    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Async
    @Scheduled(fixedRate = 5000)
    public void deleteUsers() {

        for (int i = 0; i < confirmationTokenRepository.getUnconfirmedTokens().size(); i++) {
            ConfirmationToken tempToken = confirmationTokenRepository.getUnconfirmedTokens().get(i);
            if (tempToken.getExpiresAt().isBefore(java.time.LocalDateTime.now())&&tempToken.getConfirmedAt()==null) {
                confirmationTokenRepository.delete(confirmationTokenRepository.findById(tempToken.getTokenId()).orElseThrow());
                userRepository.delete(tempToken.getUser());
            }
        }
    }
}
