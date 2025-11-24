package com.demo_project_yhlas.demo;

import com.demo_project_yhlas.config.SecurityConfig;
import com.demo_project_yhlas.controller.UserController;
import com.demo_project_yhlas.entity.User;
import com.demo_project_yhlas.exception.GlobalExceptionHandler;
import com.demo_project_yhlas.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
public class UserControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    private User sampleUser() {
        return User.builder()
                .id(1L)
                .email("example@gmail.com")
                .password("123Pass")
                .build();
    }

    @Test
    void getAllUsers_withoutAuth_returns401() throws Exception {
        mvc.perform(get("/api/users/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllUsers_withInMemoryUser_returns200() throws Exception {
        Mockito.when(userService.getAll())
                .thenReturn(List.of(sampleUser()));

        mvc.perform(get("/api/users/all")
                        .with(httpBasic("testuser", "password")) // in-memory user
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
