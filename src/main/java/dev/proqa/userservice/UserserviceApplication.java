package dev.proqa.userservice;

import dev.proqa.userservice.domain.AppUser;
import dev.proqa.userservice.domain.Role;
import dev.proqa.userservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run (UserService userservice){

        return args -> {
            userservice.saveRole(new Role(null, "ROLE_USER"));
            userservice.saveRole(new Role(null, "ROLE_MANAGER"));
            userservice.saveRole(new Role(null, "ROLE_ADMIN"));
            userservice.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));


            userservice.saveUser(new AppUser(null, "John Travolta", "john", "1234", new ArrayList<>()));
            userservice.saveUser(new AppUser(null, "Will Smith", "will", "1234", new ArrayList<>()));
            userservice.saveUser(new AppUser(null, "Jim Carry", "jim", "1234", new ArrayList<>()));
            userservice.saveUser(new AppUser(null, "Arnold Schwarzenegger", "arnold", "1234", new ArrayList<>()));


            userservice.addRoleToUser("john", "ROLE_USER");
            userservice.addRoleToUser("john", "ROLE_MANAGER");
            userservice.addRoleToUser("will", "ROLE_MANAGER");
            userservice.addRoleToUser("jim", "ROLE_ADMIN");
            userservice.addRoleToUser("arnold", "ROLE_SUPER_ADMIN");
            userservice.addRoleToUser("arnold", "ROLE_ADMIN");
            userservice.addRoleToUser("arnold", "ROLE_USER");
        };
    }

}
