package ch.noseryoung.sbdemo01.register;

import ch.noseryoung.sbdemo01.register.token.ConfirmationToken;
import ch.noseryoung.sbdemo01.register.token.ConfirmationTokenService;
import ch.noseryoung.sbdemo01.user.User;
import ch.noseryoung.sbdemo01.user.UserRole;
import ch.noseryoung.sbdemo01.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegisterService {

    private final UserService userService;
    private final EmailValidate emailValidate;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegisterRequest registerRequest) {
        if (!emailValidate.test(registerRequest.getEmail())){
            throw new IllegalStateException("invalid email");
        }
        return userService.signUpUser(
                new User(
                        registerRequest.getUsername(),
                        registerRequest.getLastName(),
                        registerRequest.getEmail(),
                        registerRequest.getPassword(),
                        UserRole.USER
                )
        );
    }
    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() -> new IllegalStateException("token cant be found"));
        if (confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email is taken");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getUser().getEmail());

        return "confirmed";
    }
}
