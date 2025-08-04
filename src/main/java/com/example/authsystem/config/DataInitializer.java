package com.example.authsystem.config;

import com.example.authsystem.entity.Role;
import com.example.authsystem.entity.User;
import com.example.authsystem.repository.RoleRepository;
import com.example.authsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize roles if they don't exist
        if (roleRepository.count() == 0) {
            Role userRole = new Role(Role.RoleName.ROLE_USER);
            Role adminRole = new Role(Role.RoleName.ROLE_ADMIN);
            
            roleRepository.save(userRole);
            roleRepository.save(adminRole);
        }
        
        // Create default admin user if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User("admin", "admin@example.com", passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            
            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(roleRepository.findByName(Role.RoleName.ROLE_ADMIN).get());
            adminRoles.add(roleRepository.findByName(Role.RoleName.ROLE_USER).get());
            admin.setRoles(adminRoles);
            
            userRepository.save(admin);
        }
        
        // Create default user if it doesn't exist
        if (!userRepository.existsByUsername("user")) {
            User user = new User("user", "user@example.com", passwordEncoder.encode("user123"));
            user.setFirstName("Regular");
            user.setLastName("User");
            
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(roleRepository.findByName(Role.RoleName.ROLE_USER).get());
            user.setRoles(userRoles);
            
            userRepository.save(user);
        }
    }
}