package org.FilRouge.backend.Securite;

import org.FilRouge.backend.Model.User;
import org.FilRouge.backend.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {
    @Bean
    CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("suilahizakaria@gmail.com").isEmpty()) {
                User admin = new User();
                admin.setFullName("zakaria");
                admin.setEmail("suilahizakaria@gmail.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");



                userRepository.save(admin);
                System.out.println("✅ Admin user created: suilahizakaria@gmail.com / admin123");
            } else {
                System.out.println("ℹ️ Admin user already exists");
            }
        };
    }
}
