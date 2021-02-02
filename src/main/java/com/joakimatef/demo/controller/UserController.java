package com.joakimatef.demo.controller;

import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.service.UserService;
import com.joakimatef.demo.service.security.permission.UserCreatePermission;
import com.joakimatef.demo.service.security.permission.UserDeletePermission;
import com.joakimatef.demo.service.security.permission.UserReadPermission;
import com.joakimatef.demo.service.security.permission.UserUpdatePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    @UserReadPermission
    public ResponseEntity<?> getAllUsersFromService(){
        return userService.getAllUsers();
    }


    @PostMapping("/post")
    @UserCreatePermission
    public ResponseEntity<User> createAdmin(@RequestBody User user){
        User newUser;
        try{
             newUser = userService.createAdmin(user);
        } catch (Exception e) {
            throw new RuntimeException("Could not create it");
        }
            return ResponseEntity.ok(newUser);
    }

    @DeleteMapping("/delete")
    @UserDeletePermission
    public ResponseEntity<?> deletedAdmin(@RequestBody User user){

        try{
            userService.deletedAdmin(user);
        } catch (Exception e) {
            throw new RuntimeException("Could not create it");
        }
            return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/edit")
    @UserUpdatePermission
    public ResponseEntity<?> updateAdmin(@RequestBody User user){

        try {
            userService.saveEditAdmin(user);
            return  ResponseEntity.ok("Admin is edit successfully!");
        } catch (Exception e){
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }
}
