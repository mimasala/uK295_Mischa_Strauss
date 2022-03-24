package ch.noseryoung.sbdemo01.user;

import ch.noseryoung.sbdemo01.register.token.ConfirmationToken;
import ch.noseryoung.sbdemo01.register.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository UserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(UserRepository.findAll());
    }

    public Optional<User> getUser(Long UserId) {
        return UserRepository.findById(UserId);
    }

    public User addUser(User User) {
        boolean exists = UserRepository.findByEmail(User.getEmail()).isPresent();
        if (!exists){
            return UserRepository.save(User);
        }else {
            throw new IllegalStateException("this email already exists");
        }
    }

    public void deleteUser(Long userID) {
        UserRepository.deleteById(userID);
    }

    @Transactional
    public User updateUser(User user, Long userID) {
        boolean exists = UserRepository.existsById(userID);
        if (!exists){
            throw new IllegalStateException("user does not exist");
        }
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        return UserRepository.updateUser(user.getUsername(), user.getLastName(), user.getEmail(), encoded, userID);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return UserRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("user with name "+username+" was not found"));
    }

    public String signUpUser(User user){
        boolean userExists = UserRepository.findByUsername(user.getUsername()).isPresent();
        if (userExists){
            throw new IllegalStateException("username already taken");
        }
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        System.out.println();
        UserRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void enableAppUser(String email) {
        UserRepository.enableUser(email);
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> exception(IllegalStateException illegal){
        return ResponseEntity.status(403).body(illegal.getMessage());
    }
}
