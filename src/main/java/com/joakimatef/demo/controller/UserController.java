package com.joakimatef.demo.controller;

import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.service.UserService;
import com.joakimatef.demo.service.security.permission.UserCreatePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
}
