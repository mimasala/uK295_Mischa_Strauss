//package ch.noseryoung.sbdemo01.user;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//@Configuration
//public class UserConfig {
//    @Bean
//    CommandLineRunner userInit(UserRepository repository) {
//        return args -> {
//            User one = new User(
//                    "Mischa",
//                    "strauss",
//                    "coool-pwd",
//                    UserRole.USER
//            );
//            User two = new User(
//                    "sky",
//                    "bollin",
//                    "not-cool-pwd",
//                    UserRole.ADMIN
//            );
//            repository.saveAll(List.of(one, two));
//        };
//    }
//}
