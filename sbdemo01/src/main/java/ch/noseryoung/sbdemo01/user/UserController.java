package ch.noseryoung.sbdemo01.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "lists all users")
    @PreAuthorize("hasAuthority('MANAGE')")
    public ResponseEntity<List<User>> findAll(){
        return userService.getUsers();
    }

    @GetMapping(path = "{userID}")
    @Operation(summary = "gets user by id")
    @PreAuthorize("hasAnyAuthority('MANAGE','CRUD')")
    public ResponseEntity<Optional<User>> findByIDController(@PathVariable Long userID){
        return ResponseEntity.ok()
                .body(userService.getUser(userID));
    }

    @PutMapping
    @Operation(summary = "adds Authority of user by name")

    public ResponseEntity<Object> addAuthToUser(
            @RequestParam String userName,
            @RequestParam String auth){
        userService.addAuthToUser(userName, auth);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{userID}")
    @Operation(summary = "deletes user by id")
    @PreAuthorize("hasAuthority('MANAGE')")
    public void deleteUser(@PathVariable Long userID){
        userService.deleteUser(userID);
    }

    @PutMapping(path = "{userID}")
    @Operation(summary = "updates user by id")
    public void updateUser(
            @PathVariable Long userID,
            @RequestBody User user){
        userService.updateUser(user, userID);
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> exception(IllegalStateException illegal){
        return ResponseEntity.status(403).body(illegal.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    ResponseEntity<String> exception(UsernameNotFoundException notfound){
        return ResponseEntity.status(403).body(notfound.getMessage());
    }
}
