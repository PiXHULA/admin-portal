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

//        return ResponseEntity.status(201).body(String.format("%s has been created", newAdmin.getUsername()));
    }

    public ResponseEntity<?> deleteAdmin(User authenticatedUser, User user) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s couldn't be found",user.getUsername())));

        if (isNotTheSameUserButHasAuthority(authenticatedUser, foundUser)) { //Superuser deleting another admin
            userRepository.delete(foundUser);
            return ResponseEntity.ok(String.format("Successfully deleted %s",foundUser.getUsername()));
        }
        return ResponseEntity.status(403).body(String.format("You're not allowed to delete %s",foundUser.getUsername()));
    }

    public ResponseEntity<?> updateAdmin(User authenticatedUser, User userToEdit) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(userToEdit.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User: %s couldn't be found",userToEdit.getUsername())));
        foundUser.setPassword(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(userToEdit.getPassword()));

        if (isNotTheSameUserButHasAuthority(authenticatedUser, foundUser)) { //Superuser updating another admin
            userRepository.save(foundUser);
            return ResponseEntity.ok(String.format("Update successful for %s",foundUser.getUsername()));
        }
        if (isTheSameUser(authenticatedUser, foundUser)) { //Admin updating itself
            userRepository.save(foundUser);
            return ResponseEntity.ok(String.format("Update successful for %s",foundUser.getUsername()));

        }

        return ResponseEntity.status(403).body(String.format("You're not allowed to update %s",foundUser.getUsername()));
    }

    public User findUserById(User user) throws UserNotFoundException {
       return userRepository.findUserById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s couldn't be found",user.getUsername())));
    }

    private boolean isTheSameUser(User authenticatedUser, User foundUser) {
        return authenticatedUser.getId().equals(foundUser.getId());
    }

    private boolean isNotTheSameUserButHasAuthority(User authenticatedUser, User foundUser) {
        return !authenticatedUser.getId().equals(foundUser.getId()) &&
                authenticatedUser.getAuthorities().toString().contains("user.update") &&
                foundUser.getAuthorities().toString().contains("user.admin.update");
    }

}
