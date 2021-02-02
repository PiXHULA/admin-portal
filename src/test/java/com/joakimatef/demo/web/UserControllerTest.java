package com.joakimatef.demo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joakimatef.demo.controller.UserController;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.service.UserService;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    User adminUser;
    Role adminRole;

    @BeforeEach
    void setUp() {
        adminRole = new Role();
        adminUser = User.builder()
                .id(1L)
                .username("admin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build();
        given(userService.createAdmin(any(User.class))).willReturn(adminUser);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void createAdmin() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(adminUser);
        System.out.println(jsonContent);
//        mockMvc.perform(post("/api/v1/user/post")
//                .param("user", jsonContent))
//                .andExpect(status().isOk());
    }
}
