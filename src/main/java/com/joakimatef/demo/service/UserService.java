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
import java.util.stream.Collectors;

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<?> getAllUsers(User user) {
        List<User> allUsers;
        if (user.getAuthorities().toString().contains("user.read")) {
            allUsers = userRepository.findAll();
        return ResponseEntity.ok(allUsers);
        } else if (user.getAuthorities().toString().contains("user.admin.read")) {
            allUsers = userRepository.findAll()
                    .stream()
                    .filter(e -> e.getAuthorities().toString().contains("user.admin.read"))
                    .collect(Collectors.toList());
        return ResponseEntity.ok(allUsers);
        } else
        return ResponseEntity.status(404).body("Not Found");
    }

    public User createAdmin(User user) {
        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
        Optional<User> alreadyExistingUser = userRepository.findByUsername(user.getUsername());
        if(alreadyExistingUser.isPresent())
            throw new RuntimeException(String.format("User with %s already exists", alreadyExistingUser.get().getUsername()));

        return userRepository.save(User.builder()
                .username(user.getUsername())
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(user.getPassword()))
                .role(adminRole)
                .build());
    }

    public ResponseEntity<?> deleteAdmin(User authenticatedUser, Long id) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User couldn't be found"));

        if (isNotTheSameUserButHasAuthority(authenticatedUser, foundUser)) { //Superuser deleting another admin
            userRepository.delete(foundUser);
            return ResponseEntity.ok(String.format("Successfully deleted %s", foundUser.getUsername()));
        }
            return ResponseEntity.status(403).body(String.format("You're not allowed to delete %s", foundUser.getUsername()));
    }

    public ResponseEntity<?> getUserToEdit(User user, Long id) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User couldn't be found"));
        if (isNotTheSameUserButHasAuthority(user, foundUser) || isTheSameUser(user,foundUser)) { //Superuser deleting another admin
            return ResponseEntity.ok().body(foundUser);
        }
        return ResponseEntity.status(403).body(String.format("You're not allowed to edit %s", foundUser.getUsername()));
    }

    public ResponseEntity<?> updateAdmin(User authenticatedUser, User userToEdit) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(userToEdit.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User: %s couldn't be found", userToEdit.getUsername())));
        foundUser.setPassword(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(userToEdit.getPassword()));

        if (isNotTheSameUserButHasAuthority(authenticatedUser, foundUser)) { //Superuser updating another admin
            userRepository.save(foundUser);
            return ResponseEntity.ok(String.format("Update successful for %s", foundUser.getUsername()));
        }
        if (isTheSameUser(authenticatedUser, foundUser)) { //Admin updating itself
            userRepository.save(foundUser);
            return ResponseEntity.ok(String.format("Update successful for %s", foundUser.getUsername()));

        }

        return ResponseEntity.status(403).body(String.format("You're not allowed to update %s", foundUser.getUsername()));
    }

    public ResponseEntity<?> findUserById(User user) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s couldn't be found", user.getUsername())));
        return ResponseEntity.ok(foundUser);
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
