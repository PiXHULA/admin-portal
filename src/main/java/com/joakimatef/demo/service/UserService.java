package com.joakimatef.demo.service;

import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.security.RoleRepository;
import com.joakimatef.demo.repository.security.UserRepository;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;

    @Autowired
    public UserService (UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User createAdmin(User user){
        Role adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));

        return userRepository.save(User.builder()
                .username(user.getUsername())
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode(user.getPassword()))
                .role(adminRole)
                .build());
    }
}
