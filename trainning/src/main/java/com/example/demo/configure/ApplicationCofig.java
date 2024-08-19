package com.example.demo.configure;

import com.example.demo.constant.PredefinedRole;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Users;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationCofig {
    @NonFinal
    static final String ADMIN_USER_NAME = "admin";
    @NonFinal
    static final String ADMIN_PASSWORD = "admin";
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RolesRepository rolesRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
//                rolesRepository.save(Roles.builder()
//                        .name(PredefinedRole.USER_ROLE)
//                        .descriptions("User role")
//                        .build());

                Roles adminRole = rolesRepository.findById(2).orElseThrow(
                        () -> new RuntimeException("Admin role not found"));
                LocalDateTime created_at = LocalDateTime.now();

                var roles = new HashSet<Roles>();
                roles.add(adminRole);
                Users users = Users.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .created_at(created_at)
                        .roles(roles)
                        .build();
                userRepository.save(users);
            }
        };
    }
}
