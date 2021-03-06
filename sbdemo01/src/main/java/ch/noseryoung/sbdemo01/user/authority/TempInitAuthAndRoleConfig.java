package ch.noseryoung.sbdemo01.user.authority;

import ch.noseryoung.sbdemo01.user.userRole.RoleRepository;
import ch.noseryoung.sbdemo01.user.userRole.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.UUID;

@Configuration
@AllArgsConstructor
public class TempInitAuthAndRoleConfig {

    private final AuthorityRepository authorityRepository;
    private final RoleRepository roleRepository;

    @Bean
    public void initAuth(){
        authorityRepository.save(
                new Authority(UUID.randomUUID(),"CRUD")
        );
        authorityRepository.save(
                new Authority(UUID.randomUUID(),"MANAGE")
        );
        authorityRepository.save(
                new Authority(UUID.randomUUID(),"READONLY")
        );
    }

    @Bean
    public void initRole(){
        roleRepository.save(
                new UserRole(UUID.randomUUID(), "ADMIN", authorityRepository.findAll())
        );
        roleRepository.save(
                new UserRole(UUID.randomUUID(), "USER", List.of(authorityRepository.findByName("CRUD").get()))
        );
        roleRepository.save(
                new UserRole(UUID.randomUUID(), "VISITOR", List.of(authorityRepository.findByName("READONLY").get()))
        );
    }
//    new UserRole(UUID.randomUUID(), "USER", authorityRepository.findByName("CRUD"));
//    new UserRole(UUID.randomUUID(), "ADMIN", authorityRepository.findAll());

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> exception(IllegalStateException illegal){
        return ResponseEntity.status(403).body(illegal.getMessage());
    }
}
