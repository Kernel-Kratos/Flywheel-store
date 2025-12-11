package com.shoppingbackend.flywheel_store.data;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.shoppingbackend.flywheel_store.model.Role;
import com.shoppingbackend.flywheel_store.model.User;
import com.shoppingbackend.flywheel_store.repository.RoleRepository;
import com.shoppingbackend.flywheel_store.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("Role_Admin", "Role_Customer");
        createDefautUserIfNotExists();
        createDefautAdminIfNotExists();
        createDefautRoleIfNotExits(defaultRoles);
    }

    private void createDefautUserIfNotExists() {
        Role userRole = roleRepository.findByRoleName("Role_Customer").get();
        for (int i = 0; i <= 5; i++){
            String defaultEmail = "user" +i+ "@gamil.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + "created successfully.");

        }
    }

    private void createDefautAdminIfNotExists() {
        Role userRole = roleRepository.findByRoleName("Role_Admin").get();
        for (int i = 0; i <= 5; i++){
            String defaultEmail = "admin" +i+ "@gamil.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("987654"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + "created successfully.");

        }
    }


    private void createDefautRoleIfNotExits(Set <String> roles){
        roles.stream()
                .filter(role -> roleRepository.findByRoleName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
