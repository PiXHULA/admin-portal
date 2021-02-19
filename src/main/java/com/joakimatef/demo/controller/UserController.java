package com.joakimatef.demo.controller;

import com.joakimatef.demo.exceptions.UserNotFoundException;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.service.UserService;
import com.joakimatef.demo.service.security.permission.UserCreatePermission;
import com.joakimatef.demo.service.security.permission.UserDeletePermission;
import com.joakimatef.demo.service.security.permission.UserReadPermission;
import com.joakimatef.demo.service.security.permission.UserUpdatePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


/**
 * Controller that will handle all of the applications rest APIs
 *
 * @author Atef Sendesni
 * @since 12.01.2021
 */

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    /*
     * UserService used for CRUD operations for users
     */
    UserService userService;
    /*
     * User loads credentials from login
     */
    User authenticatedUser;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Calls the {@link SecurityContextHolder} to get which user is authenticated.
     * Calls the {@link UserService} to handle the request to get all the users
     *
     * @return List of users that are persisted on the database else error response from the service.
     */

    @GetMapping("/users")
    @UserReadPermission
    public ResponseEntity<?> getAllUsers() {
        User authenticatedUser = new User();
        try {
            authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getAllUsers(authenticatedUser);
        } catch (Exception e) {
            return userService.getAllUsers(authenticatedUser);
        }
    }

    /**
     * Calls the {@link SecurityContextHolder} to get which user is authenticated.
     * Calls the {@link UserService} to handle the request to get one user to edit
     *
     * @param id id
     * @return User that are persisted on the database else error response.
     */
    @GetMapping("/user/{id}")
    @UserReadPermission
    public ResponseEntity<?> getUserToEdit(@PathVariable Long id) {
        User authenticatedUser;
        try {
            authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.getUserToEdit(authenticatedUser ,id);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    /**
     * Calls the {@link SecurityContextHolder} to get which user is authenticated.
     * Calls the {@link UserService} to handle the request to get the current user
     *
     * @return User that are persisted on the database else error response.
     */
    @GetMapping("/user")
    @UserReadPermission
    public ResponseEntity<?> getCurrentUser() {
        User authenticatedUser;
        try {
            authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.findUserById(authenticatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Not Found");
        }
    }

    /**
     * Calls the {@link UserService} to handle the request to create and persist new user to database
     *
     * @param user New user.
     * @return User that was persisted to the database if successfully else return error response.
     */

    @PostMapping("/post")
    @UserCreatePermission
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        User newAdmin;
        try {
            newAdmin = userService.createAdmin(user);
            return ResponseEntity.status(201).body(String.format("%s has been created", newAdmin.getUsername()));
        } catch (RuntimeException e){
            return ResponseEntity.status(400).body("User already exists!");
        } catch (Exception e) {
            return ResponseEntity.status(403).body("You're not allowed to create another user");
        }
    }

    /**
     * Calls the {@link SecurityContextHolder} to get which user is authenticated.
     * Calls the {@link UserService} to handle the request for deleting a user.
     *
     * @param id id.
     * @return Response from service if the user is deleted successfully or if failed.
     */

    @DeleteMapping("/delete/{id}")
    @UserDeletePermission
    public ResponseEntity<?> deletedAdmin(@PathVariable Long id) throws UserNotFoundException {
        try {
            authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.deleteAdmin(authenticatedUser, id);
        } catch (Exception e) {
            return userService.deleteAdmin(authenticatedUser, id);
        }
    }

    /**
     * Calls the {@link SecurityContextHolder} to get which user is authenticated.
     * Calls the {@link UserService} to handle the request for edit and persist edit user to database
     *
     * @param user user.
     * @return Edit user if successfully else return error response from service.
     */
    @PatchMapping("/edit")
    @UserUpdatePermission
    public ResponseEntity<?> updateAdmin(@RequestBody User user) throws UserNotFoundException {
        User authenticatedUser = new User();
        try {
            authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userService.updateAdmin(authenticatedUser, user);
        } catch (Exception e) {
            return userService.updateAdmin(authenticatedUser, user);
        }
    }
}
