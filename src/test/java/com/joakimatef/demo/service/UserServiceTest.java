package com.joakimatef.demo.service;

import com.joakimatef.demo.bootstrap.exceptions.UserNotFoundException;
import com.joakimatef.demo.domain.security.Authority;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.security.RoleRepository;
import com.joakimatef.demo.repository.security.UserRepository;
import com.joakimatef.demo.service.security.JpaUserDetailService;
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
    User adminUser2;
    User suAdminUser;
    User suAdminUser2;
    Role adminRole;
    Role suAdminRole;
    List<User> allUsersList;

    @BeforeEach
    void setUp() {
        allUsersList = new ArrayList<>();
        adminRole = Role.builder()
                .authority(Authority.builder().permission("user.admin.read").build())
                .authority(Authority.builder().permission("user.admin.update").build())
                .build();

        suAdminRole = Role.builder()
                .authority(Authority.builder().permission("user.read").build())
                .authority(Authority.builder().permission("user.update").build())
                .authority(Authority.builder().permission("user.delete").build())
                .authority(Authority.builder().permission("user.create").build())
                .build();

        adminUser = User.builder()
                .id(1L)
                .username("adminUser")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build();

        adminUser2 = User.builder()
                .id(2L)
                .username("adminUser2")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build();

        suAdminUser = User.builder()
                .id(3L)
                .username("suAdminUser")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(suAdminRole)
                .build();

        suAdminUser2 = User.builder()
                .id(4L)
                .username("suAdminUser2")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(suAdminRole)
                .build();

        allUsersList.add(adminUser);
    }

    @Test
    void createAdmin() {
        //given
        given(roleRepository.findByRoleName(anyString())).willReturn(Optional.of(adminRole));
        given(userRepository.save(any(User.class))).willReturn(adminUser);

        //when
        userService.createAdmin(adminUser);

        //then
        then(userRepository).should().save(any(User.class));
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
    void deleteAdmin() throws UserNotFoundException {
        //given
        given(userRepository.findUserById(anyLong())).willReturn(Optional.of(adminUser));

        //when
        userService.deleteAdmin(suAdminUser,adminUser);

        //then
        then(userRepository).should().delete(adminUser);
        then(userRepository).shouldHaveNoMoreInteractions();
    }


    @Test
    void saveEditAdminTest() throws UserNotFoundException {

        //given
        given(userRepository.findUserById(anyLong())).willReturn(Optional.of(adminUser));

        //when
        adminUser.setUsername("newUsername");
        userService.updateAdmin(suAdminUser,adminUser);
        Optional<User> userById = userRepository.findUserById(adminUser.getId());

        //then
        then(userRepository).should().save(adminUser);
        assertEquals("newUsername",userById.get().getUsername());
        assertNotEquals("originalName",userById.get().getUsername());
    }
}
