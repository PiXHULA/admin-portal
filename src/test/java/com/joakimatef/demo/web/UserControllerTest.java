package com.joakimatef.demo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joakimatef.demo.bootstrap.exceptions.UserNotFoundException;
import com.joakimatef.demo.domain.security.Authority;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.repository.security.UserRepository;
import com.joakimatef.demo.service.UserService;
import com.joakimatef.demo.service.security.JpaUserDetailService;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import com.joakimatef.demo.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {

    public static final String API_URI = "/api/v1/user/";

    @Autowired
    WebApplicationContext wac;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    TestEntityManager testEntityManager;

    MockMvc mockMvc;

    User adminUser;
    User suAdminUser;
    Role adminRole;
    Role suAdminRole;
    final ObjectMapper mapper = new ObjectMapper();
    String jsonContent;
    @BeforeEach
    void setUp() throws JsonProcessingException {


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
                .username("admin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build();

        suAdminUser = User.builder()
                .id(2L)
                .username("suadmin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(suAdminRole)
                .build();

        given(userService.createAdmin(any(User.class))).willReturn(adminUser);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();

        jsonContent = mapper.writeValueAsString(adminUser);
    }

    @Test
    @DisplayName("It will create admin with the correct authority")
    @WithMockUser(authorities = {"user.create"})
    void createAdminWithCreateAuth() throws Exception {
        given(userService.createAdmin(any(User.class))).willReturn(adminUser);

        mockMvc.perform(post(API_URI +"post")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("It will not create admin without the correct authority")
    @WithMockUser(authorities = {"user.read"})
    void createAdminWithReadAuth() throws Exception {
        given(userService.createAdmin(any(User.class))).willReturn(adminUser);

        mockMvc.perform(post(API_URI + "post")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("It will list all users with the read authority")
    @WithMockUser(authorities = {"user.read","user.admin.read"})
    void getAllUsersFromService() throws Exception {
        mockMvc.perform(get(API_URI + "users"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It will delete admin with the correct authority")
    @WithMockUser(authorities = {"user.delete"})
    void deletedAdminWithDeleteAuth() throws Exception {

        mockMvc.perform(delete(API_URI + "delete/" + adminUser.getId())
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It will not delete admin without the correct authority")
    @WithMockUser(authorities = {"user.read"})
    void deletedAdminWithReadAuth() throws Exception {
        mockMvc.perform(delete(API_URI + "delete/" + adminUser.getId())
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("It will update admin with the correct authority")
    @WithMockUser(authorities = {"user.update"})
    void updateAdminWithUpdateAuth() throws Exception {
        mockMvc.perform(patch(API_URI + "edit")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It will not update admin without the correct authority")
    @WithMockUser(authorities = {"user.read"})
    void updateAdminWithReadAuth() throws Exception {
        mockMvc.perform(patch(API_URI + "edit")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
