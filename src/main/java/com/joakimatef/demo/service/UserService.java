package com.joakimatef.demo.service;

import com.joakimatef.demo.exceptions.UserAlreadyExistsException;
import com.joakimatef.demo.exceptions.UserNotFoundException;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.RoleRepository;
import com.joakimatef.demo.repository.UserRepository;
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

    /*
     * UserRepository used for CRUD operations for users
     */
   private final UserRepository userRepository;

    /*
     * RoleRepository used for CRUD operations for users
     */
   private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Calls the {@link UserRepository} to get all the admins that are persisted if the user is an SUPERADMIN.
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
            allUsers = new ArrayList<>();
            allUsers.add(user);
        return ResponseEntity.ok(allUsers);
        } else
        return ResponseEntity.status(404).body("Not Found");
    }

    /**
     * Calls the {@link RoleRepository} to fetch the ADMIN role.
     * Calls the {@link UserRepository} to check if userName is already taken.
     * Calls the {@link UserRepository} to create and save Admin with ADMIN role.
     *
     * @param user New user
     * @return The user that is added to the database.
     */

    public User createAdmin(User user) throws UserAlreadyExistsException {
        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
        Optional<User> alreadyExistingUser = userRepository.findByUsername(user.getUsername().toLowerCase());
        if(alreadyExistingUser.isPresent())
            throw new UserAlreadyExistsException(String.format("User with %s already exists", alreadyExistingUser.get().getUsername()));

        return userRepository.save(User.builder()
                .username(user.getUsername().toLowerCase())
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(user.getPassword()))
                .role(adminRole)
                .build());
    }

    /**
     * Calls the {@link UserRepository} and delete the admin.
     *
     * @param authenticatedUser The user who is logged in.
     * @param id The id of the user to delete.
     * @return An OK response with a message.
     * @throws com.joakimatef.demo.exceptions.UserNotFoundException if the user doesn't exist.
     */

    public ResponseEntity<?> deleteAdmin(User authenticatedUser, Long id) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User couldn't be found"));

        if (isNotTheSameUserButHasAuthority(authenticatedUser, foundUser)) {
            userRepository.delete(foundUser);
            return ResponseEntity.ok(String.format("Successfully deleted %s", foundUser.getUsername()));
        }
            return ResponseEntity.status(403).body(String.format("You're not allowed to delete %s", foundUser.getUsername()));
    }

    /**
     * Calls the {@link UserRepository} and delete the admin.
     *
     * @param user user The user who is logged in.
     * @param id long The id of the user to edit.
     * @return An OK response with a message or 403 if user is not allowed to do the operation.
     * @throws com.joakimatef.demo.exceptions.UserNotFoundException if the user doesn't exist.
     */

    public ResponseEntity<?> getUserToEdit(User user, Long id) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User couldn't be found"));
        if (isNotTheSameUserButHasAuthority(user, foundUser) || isTheSameUser(user,foundUser)) {
            return ResponseEntity.ok().body(foundUser);
        }
        return ResponseEntity.status(403).body(String.format("You're not allowed to edit %s", foundUser.getUsername()));
    }

    public ResponseEntity<?> updateAdmin(User authenticatedUser, User userToEdit) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(userToEdit.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User: %s couldn't be found", userToEdit.getUsername())));
        foundUser.setPassword(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(userToEdit.getPassword()));

        if (isNotTheSameUserButHasAuthority(authenticatedUser, foundUser)) {
            userRepository.save(foundUser);
            return ResponseEntity.ok(String.format("Update successful for %s", foundUser.getUsername()));
        }
        if (isTheSameUser(authenticatedUser, foundUser)) {
            userRepository.save(foundUser);
            return ResponseEntity.ok(String.format("Update successful for %s", foundUser.getUsername()));

        }

        return ResponseEntity.status(403).body(String.format("You're not allowed to update %s", foundUser.getUsername()));
    }

    /**
     * Calls the {@link UserRepository} and delete the admin.
     *
     * @param user user The user who is logged in.
     * @return An OK response with the user
     * @throws com.joakimatef.demo.exceptions.UserNotFoundException if the user doesn't exist.
     */

    public ResponseEntity<?> findUserById(User user) throws UserNotFoundException {
        User foundUser = userRepository.findUserById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s couldn't be found", user.getUsername())));
        return ResponseEntity.ok(foundUser);
    }

    /**
     * @param authenticatedUser Logged in user
     * @param foundUser user receiving the operation
     * @return true or false depending on if it's the same user
     **/

    private boolean isTheSameUser(User authenticatedUser, User foundUser) {
        return authenticatedUser.getId().equals(foundUser.getId());
    }
    /**
     * @param authenticatedUser Logged in user
     * @param foundUser user receiving the operation
     * @return true or false depending on is the authenticatedUser has the authority to do the operation
     **/
    private boolean isNotTheSameUserButHasAuthority(User authenticatedUser, User foundUser) {
        return !authenticatedUser.getId().equals(foundUser.getId()) &&
                authenticatedUser.getAuthorities().toString().contains("user.update");
    }

}
