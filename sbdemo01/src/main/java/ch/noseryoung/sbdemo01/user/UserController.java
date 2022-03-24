package ch.noseryoung.sbdemo01.user;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping
    @Operation(summary = "lists all users")
    public ResponseEntity<List<User>> findAll(){
        return userService.getUsers();
    }

    @GetMapping(path = "{userID}")
    @Operation(summary = "gets user by id")
    public ResponseEntity<Optional<User>> findByIDController(@PathVariable Long userID){
        return ResponseEntity.ok()
                .body(userService.getUser(userID));
    }

    @DeleteMapping(path = "{userID}")
    @Operation(summary = "deletes user by id")
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
}
