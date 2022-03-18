package ch.noseryoung.sbdemo01.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private final UserRepository UserRepository;

    @Autowired
    public UserService(ch.noseryoung.sbdemo01.user.UserRepository UserRepository){
        this.UserRepository = UserRepository;
    }

    public List<User> getUsers() {
        return UserRepository.findAll();
    }

    public User getUser(Long UserId) {
        return UserRepository.getById(UserId);
    }

    public void addUser(User User) {
        UserRepository.save(User);
    }

    public void deleteUser(Long UserID) {
        UserRepository.deleteById(UserID);
    }

    @Transactional
    public void updateUser(Long UserID, String name, String password) {
        UserRepository.updateUser(name,password, UserID);
    }
}
