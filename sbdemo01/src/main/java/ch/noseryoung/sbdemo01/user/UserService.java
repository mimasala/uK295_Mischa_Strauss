package ch.noseryoung.sbdemo01.user;

import ch.noseryoung.sbdemo01.register.token.ConfirmationToken;
import ch.noseryoung.sbdemo01.register.token.ConfirmationTokenService;
import ch.noseryoung.sbdemo01.user.authority.Authority;
import ch.noseryoung.sbdemo01.user.authority.AuthorityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;


@Service
@AllArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final AuthorityRepository authorityRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<List<User>> getUsers() {
        logger.trace("showing all existing users");
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    public Optional<User> getUser(Long UserId) {
        logger.trace("showing the user with {} ", UserId);
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
        User user = userRepository.findByUsername(username).orElseThrow(() -> new
                UsernameNotFoundException("user with name "+username+" was not found"));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getUserRole().getAuthorities().forEach(authority ->
                authorities.add(new SimpleGrantedAuthority(authority.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
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

    public void addAuthToUser(String userName, String authName){
        User user = userRepository.findByUsername(userName).orElseThrow(() ->
                new UsernameNotFoundException("username not found "));
        Authority authority =  authorityRepository.findByName(authName).orElseThrow(() ->
                new UsernameNotFoundException("authority name not found"));
        user.getUserRole().getAuthorities().add(authority);
        userRepository.save(user);
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> exception(IllegalStateException illegal){
        log.error(illegal.getMessage());
        return ResponseEntity.status(400).body(illegal.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<String> exception(UsernameNotFoundException notfound){
        log.error(notfound.getMessage());
        return ResponseEntity.status(404).body(notfound.getMessage());
    }
}