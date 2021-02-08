package com.joakimatef.demo.service;

import com.joakimatef.demo.bootstrap.exceptions.UserNotFoundException;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.security.RoleRepository;
import com.joakimatef.demo.repository.security.UserRepository;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;

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

    public ResponseEntity<?> createAdmin(User user) {
        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));

        User newAdmin = userRepository.save(User.builder()
                .username(user.getUsername())
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(user.getPassword()))
                .role(adminRole)
                .build());

        return ResponseEntity.status(201).body(String.format("%s has been created", newAdmin.getUsername()));
    }

    public void deletedAdmin(User user) throws UserNotFoundException {
        User foundUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException(String.format("User: %s couldn't be found",user.getUsername())));

        userRepository.delete(foundUser);
    }

    public ResponseEntity<?> updateAdmin(User authenticatedUser, User userToEdit) throws UserNotFoundException {

        User foundUser = userRepository.findUserById(userToEdit.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User: %s couldn't be found",userToEdit.getUsername())));
        foundUser.setPassword(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(userToEdit.getPassword()));

            if (isAuthUserAllowedToUpdateAnotherAdmin(authenticatedUser, foundUser)) { //Superuser updating another admin
                userRepository.save(foundUser);
                return ResponseEntity.ok(String.format("Update successful for %s by %s",foundUser.getUsername(),authenticatedUser.getUsername()));

            }
            if (isAuthUserTryingToUpdateItself(authenticatedUser, foundUser)) { //Admin updating itself
                userRepository.save(foundUser);
               return ResponseEntity.ok(String.format("Update successful for %s",foundUser.getUsername()));

            }

           return ResponseEntity.status(403).body(String.format("You're not allowed to update %s",foundUser.getUsername()));

    }

    private boolean isAuthUserTryingToUpdateItself(User authenticatedUser, User foundUser) {
        return authenticatedUser.getId().equals(foundUser.getId());
    }

    private boolean isAuthUserAllowedToUpdateAnotherAdmin(User authenticatedUser, User foundUser) {
        return !authenticatedUser.getId().equals(foundUser.getId()) &&
                authenticatedUser.getAuthorities().toString().contains("user.update") &&
                foundUser.getAuthorities().toString().contains("user.admin.update");
    }
}
