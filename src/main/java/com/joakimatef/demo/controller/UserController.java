package com.joakimatef.demo.controller;

import com.joakimatef.demo.bootstrap.exceptions.UserNotFoundException;
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

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    UserService userService;
    User authenticatedUser;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


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
