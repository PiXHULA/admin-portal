package com.joakimatef.demo.service;

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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
    List<User> allUsersList;

    @BeforeEach
    void setUp() {
        allUsersList = new ArrayList<>();
        adminRole = new Role();
        adminUser = User.builder()
                .id(1L)
                .username("adminUser")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build();

        allUsersList.add(adminUser);
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
        assertEquals("adminUser",createdUser.getUsername());
        assertTrue(createdUser.getRoles().contains(adminRole));
    }

    @Test
    void getAllUsers() {
        //given
        given(userRepository.findAll()).willReturn(allUsersList);

        //when
        ResponseEntity<?> allUsers = userService.getAllUsers();

        //then
        then(userRepository).should().findAll();
        then(userRepository).shouldHaveNoMoreInteractions();
        assertTrue(allUsers.getStatusCode().is2xxSuccessful());


    }

    @Test
    void deletedAdmin() {
        //given
        given(userRepository.findByUsername(anyString())).willReturn(Optional.of(adminUser));

        //when
        userService.deletedAdmin(adminUser);

        //then
        then(userRepository).should().delete(adminUser);
        then(userRepository).shouldHaveNoMoreInteractions();

    }

    @Test
    void saveEditAdmin() {
        //given
        given(userRepository.findUserById(anyLong())).willReturn(Optional.of(adminUser));

        //when
        adminUser.setUsername("newUsername");
        userService.saveEditAdmin(adminUser);
        Optional<User> userById = userRepository.findUserById(adminUser.getId());

        //then
        then(userRepository).should().save(adminUser);
        assertEquals("newUsername",userById.get().getUsername());
        assertNotEquals("originalName",userById.get().getUsername());

    }
}
