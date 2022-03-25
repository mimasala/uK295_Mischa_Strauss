package ch.noseryoung.sbdemo01.user;

import ch.noseryoung.sbdemo01.register.token.ConfirmationToken;
import ch.noseryoung.sbdemo01.register.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<List<User>> getUsers() {
        logger.trace("showing all existing users");
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    public Optional<User> getUser(Long UserId) {
        logger.trace("showing the user with id "+ UserId);
        return userRepository.findById(UserId);
    }

    public void deleteUser(Long userID) {
        exists(userID);
        logger.trace("deleted user");
        userRepository.deleteUserConstraint(userID);
        userRepository.delete(userRepository.findById(userID).orElseThrow(()->new IllegalStateException("user does not exist")));
    }

    @Transactional
    public void updateUser(User user, Long userID) {
        exists(userID);
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        userRepository.updateUser(user.getUsername(), user.getLastName(), user.getEmail(), encoded, userID);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("user with name "+username+" was not found"));
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new
//                UsernameNotFoundException("user with name "+username+" was not found"));
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        user.getUserRole().getAuthorities().forEach(authority ->
//                authorities.add(new SimpleGrantedAuthority(authority.getName())));
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public String signUpUser(User user){
        boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();
        if (userExists){
            throw new IllegalStateException("username already taken");
        }
        String encoded = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encoded);
        System.out.println();
        userRepository.save(user);

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
        userRepository.enableUser(email);
    }

    public void exists(Long id){
        if (!userRepository.existsById(id)){
            throw new IllegalStateException("user does not exist");
        }
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> exception(IllegalStateException illegal){
        return ResponseEntity.status(400).body(illegal.getMessage());
    }
}
