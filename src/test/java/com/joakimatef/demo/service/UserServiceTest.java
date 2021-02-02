package com.joakimatef.demo.service;

import com.joakimatef.demo.domain.security.Authority;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.security.RoleRepository;
import com.joakimatef.demo.repository.security.UserRepository;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    UserService userService;

    User adminUser;
    Role adminRole;

    @BeforeEach
    void setUp() {
        adminRole = new Role();
        adminUser = User.builder()
                .username("admin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build();

    }

    @Test
    void createAdmin() {
        //given
        given(roleRepository.findByRoleName(anyString())).willReturn(Optional.of(adminRole));
        given(userRepository.save(any(User.class))).willReturn(adminUser);

        //when
        User createdUser = userService.createAdmin(adminUser);

        //then
        then(userRepository).should().save(any(User.class));
        assertEquals("admin",createdUser.getUsername());

    }
}
