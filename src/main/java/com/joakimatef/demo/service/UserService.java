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


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The service layer that handles the business logic of the application.
 *
 * @author Atef Sendesni
 * @author Joakim Önnhage briceno
 * @since 12.01.2021
 */

@Service
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Calls the {@link com.joakimatef.demo.repository.security.UserRepository} to get all the admins that are persisted if the user is an SUPERADMIN.
     * Else the user will only ses it self
     *
     * @param user user
     * @return a List of Users or user.
     */

    public ResponseEntity<?> getAllUsers(User user) {
        List<User> allUsers;
        if (user.getAuthorities().toString().contains("user.read")) {
            allUsers = userRepository.findAll();
        return ResponseEntity.ok(allUsers);
        } else if (user.getAuthorities().toString().contains("user.admin.read")) {
//            allUsers = userRepository.findAll()
//                    .stream()
//                    .filter(e -> e.getAuthorities().toString().contains("user.admin.read"))
//                    .collect(Collectors.toList());
            allUsers = new ArrayList<>();
            allUsers.add(user);
        return ResponseEntity.ok(allUsers);
        } else
        return ResponseEntity.status(404).body("Not Found");
    }

    /**
     * Calls the {@link com.joakimatef.demo.repository.security.RoleRepository} to fetch the ADMIN role.
     * Calls the {@link com.joakimatef.demo.repository.security.UserRepository} to check if userName is already taken.
     * Calls the {@link com.joakimatef.demo.repository.security.UserRepository} to create and save Admin with ADMIN role.
     *
     * @param user New user
     * @return The user that is added to the database.
     */

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

    /**
     * Calls the {@link com.joakimatef.demo.repository.security.UserRepository} and delete the admin.
     *
     * @param authenticatedUser user The user who is logged in.
     * @param id long The id of the user to delete.
     * @return An OK response with a message.
     * @throws com.joakimatef.demo.bootstrap.exceptions.UserNotFoundException if the user doesn't exist.
     */

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
                authenticatedUser.getAuthorities().toString().contains("user.update");
    }

}
