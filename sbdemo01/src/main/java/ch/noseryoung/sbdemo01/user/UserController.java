package ch.noseryoung.sbdemo01.user;

import ch.noseryoung.sbdemo01.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService UserService;

    @Autowired
    public UserController(UserService UserService){
        this.UserService = UserService;
    }

    @GetMapping
    public List<User> findAll(){
        return UserService.getUsers();
    }

    @GetMapping(path = "{UserID}")
    public ResponseEntity<Optional<User>> findByIDController(@PathVariable Long UserID){
        return ResponseEntity.ok()
                .body(UserService.getUser(UserID));
    }

    @PostMapping
    public void createNewUser(@RequestBody User User){
        UserService.addUser(User);
    }

    @DeleteMapping(path = "{UserID}")
    public void deleteUser(@PathVariable Long UserID){
        UserService.deleteUser(UserID);
    }

    @PutMapping(path = "{UserID}")
    public void updateUser(
            @PathVariable Long UserID,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String password){
        UserService.updateUser(UserID,name,password);
    }
}
