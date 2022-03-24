package ch.noseryoung.sbdemo01.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/v1/user/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return userService.getUsers();
    }

    @GetMapping(path = "{userID}")
    public ResponseEntity<Optional<User>> findByIDController(@PathVariable Long userID){
        return ResponseEntity.ok()
                .body(userService.getUser(userID));
    }

    @DeleteMapping(path = "{userID}")
    public void deleteUser(@PathVariable Long userID){
        userService.deleteUser(userID);
    }

    @PutMapping(path = "{userID}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long userID,
            @RequestBody User user){
        return ResponseEntity.ok().body(userService.updateUser(user, userID));
    }
}
