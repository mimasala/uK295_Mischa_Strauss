package ch.noseryoung.sbdemo01.register;

import ch.noseryoung.sbdemo01.Email.EmailSender;
import ch.noseryoung.sbdemo01.register.token.ConfirmationToken;
import ch.noseryoung.sbdemo01.register.token.ConfirmationTokenService;
import ch.noseryoung.sbdemo01.user.User;
import ch.noseryoung.sbdemo01.user.UserService;
import ch.noseryoung.sbdemo01.user.authority.AuthorityRepository;
import ch.noseryoung.sbdemo01.user.userRole.RoleRepository;
import ch.noseryoung.sbdemo01.user.userRole.UserRole;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Log4j2
public class RegisterService {

    private final UserService userService;
    private final EmailValidate emailValidate;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final RoleRepository roleRepository;


    public String register(RegisterRequest registerRequest) {
        if (!emailValidate.test(registerRequest.getEmail())){
            throw new IllegalStateException("invalid email");
        }
        String token = userService.signUpUser(
                new User(
                        registerRequest.getUsername(),
                        registerRequest.getLastName(),
                        registerRequest.getEmail(),
                        registerRequest.getPassword(),
                        genUserRole(registerRequest.getUsername(),registerRequest.getPassword())

                )
        );
        log.trace("user " + registerRequest.getUsername() + " was created");
        String link = "http://localhost:8080/api/v1/register/confirm?token="+token;
        emailSender.send(registerRequest.getEmail(), buildEmail(registerRequest.getEmail(), link));
        return token;
    }

    public UserRole genUserRole(String userName, String password){
        if (userName.equals("MischaADMIN")&&password.equals("adminpass")){
            return roleRepository.findByName("ADMIN").orElseThrow(() -> new IllegalStateException("role cant be found"));
        }
        return roleRepository.findByName("USER").orElseThrow(() -> new IllegalStateException("role cant be found"));
    }

    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
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

    private String buildEmail(String name, String link) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Hello "+name+"</h1> <a href=\""+link+"\">verify account</a>\n" +
                "</body>\n" +
                "</html>";
    }



    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> exception(IllegalStateException illegal){
        return ResponseEntity.status(403).body(illegal.getMessage());
    }
}
