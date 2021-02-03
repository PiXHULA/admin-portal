package com.joakimatef.demo.service;

import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.security.RoleRepository;
import com.joakimatef.demo.repository.security.UserRepository;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return ResponseEntity.ok(allUsers);
    }

    public User createAdmin(User user) {
        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));

        return userRepository.save(User.builder()
                .username(user.getUsername())
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(user.getPassword()))
                .role(adminRole)
                .build());
    }

    public void deletedAdmin(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new NullPointerException("Admin not found"));

        userRepository.delete(foundUser);
    }

    public void saveEditAdmin(User editAdmin) {
        User getUserToEdit = userRepository.findUserById(editAdmin.getId())
                .orElseThrow(() -> new NullPointerException("Admin not found"));

        getUserToEdit.setUsername(editAdmin.getUsername());
        getUserToEdit.setPassword(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(editAdmin.getPassword()));

        userRepository.save(getUserToEdit);
    }

    public ResponseEntity<?> saveEditAdmin2(Authentication authentication, @RequestBody User user) {
        User authenticationUser = (User) authentication.getPrincipal();
        User getUserToEdit = userRepository.findUserById(user.getId())
                .orElseThrow(() -> new NullPointerException("Admin not found"));

            if (authenticationUser.getId().equals(getUserToEdit.getId()) &&
                    authenticationUser.getAuthorities().toString().contains("user.update")) {

                getUserToEdit.setUsername(user.getUsername());
                getUserToEdit.setPassword(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(user.getPassword()));
                userRepository.save(getUserToEdit);
               return ResponseEntity.ok("SuperAdmin is edit successfully!");

            }
            if (!authenticationUser.getId().equals(getUserToEdit.getId()) &&
                    authenticationUser.getAuthorities().toString().contains("user.update") &&
                    getUserToEdit.getAuthorities().toString().contains("user.admin.update")) {

                getUserToEdit.setUsername(user.getUsername());
                getUserToEdit.setPassword(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(user.getPassword()));
                userRepository.save(getUserToEdit);
                return ResponseEntity.ok("SuperAdmin edit Admin successfully!");


            }
            if (authenticationUser.getId().equals(getUserToEdit.getId())) {

                getUserToEdit.setUsername(user.getUsername());
                getUserToEdit.setPassword(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(user.getPassword()));
                userRepository.save(getUserToEdit);
               return ResponseEntity.ok("Admin edit successfully!");

            }

           return ResponseEntity.of(Optional.of("Not Allowed"));

    }

}
