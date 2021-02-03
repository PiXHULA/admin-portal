package com.joakimatef.demo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joakimatef.demo.domain.security.Role;
import com.joakimatef.demo.domain.security.User;
import com.joakimatef.demo.service.UserService;
import com.joakimatef.demo.service.security.PasswordEncoderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserControllerTest {


    public static final String API_URI = "/api/v1/user/";

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @MockBean
    UserService userService;

    User adminUser;
    Role adminRole;
    final ObjectMapper mapper = new ObjectMapper();
    String jsonContent;
    @BeforeEach
    void setUp() throws JsonProcessingException {
        adminRole = new Role();
        adminUser = User.builder()
                .id(1L)
                .username("admin")
                .password(PasswordEncoderFactory.createDelegatingPasswordEncoder().encode("guru"))
                .role(adminRole)
                .build();
        given(userService.createAdmin(any(User.class))).willReturn(adminUser);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();

        jsonContent = mapper.writeValueAsString(adminUser);
    }

    @Test
    @DisplayName("It will create admin with correct authority")
    @WithMockUser(authorities = {"user.create"})
    void createAdminWithCreateAuth() throws Exception {
        mockMvc.perform(post(API_URI +"post")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It will not create admin without correct authority")
    @WithMockUser(authorities = {"user.read"})
    void createAdminWithReadAuth() throws Exception {
        mockMvc.perform(post(API_URI + "post")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("It will list all users with read authority")
    @WithMockUser(authorities = {"user.read"})
    void getAllUsersFromService() throws Exception {
        mockMvc.perform(get(API_URI + "users"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It will delete admin with correct authority")
    @WithMockUser(authorities = {"user.delete"})
    void deletedAdminWithDeleteAuth() throws Exception {
        mockMvc.perform(delete(API_URI + "delete")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It will not delete admin without correct authority")
    @WithMockUser(authorities = {"user.read"})
    void deletedAdminWithReadAuth() throws Exception {
        mockMvc.perform(delete(API_URI + "delete")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("It will update admin with correct authority")
    @WithMockUser(authorities = {"user.update"})
    void updateAdminWithUpdateAuth() throws Exception {
        mockMvc.perform(patch(API_URI + "edit")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("It will not update admin without correct authority")
    @WithMockUser(authorities = {"user.read"})
    void updateAdminWithReadAuth() throws Exception {
        mockMvc.perform(patch(API_URI + "edit")
                .content(jsonContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
