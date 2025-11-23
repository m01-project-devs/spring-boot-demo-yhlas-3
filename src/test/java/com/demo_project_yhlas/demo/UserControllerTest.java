package com.demo_project_yhlas.demo;

import com.demo_project_yhlas.controller.UserController;
import com.demo_project_yhlas.dto.response.UserResponse;
import com.demo_project_yhlas.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Test
    void getUserByEmail_returnsUser() throws Exception {
        UserResponse example = new UserResponse("example@gmail.com");

        Mockito.when(userService.getByEmail("example@gmail.com"))
                .thenReturn(Optional.of(example));

        mvc.perform(get("/api/users")
                        .param("email", "example@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("example@gmail.com")));
    }

    @Test
    void getUserByEmail_notFound_returns404() throws Exception {
        Mockito.when(userService.getByEmail("missing@gmail.com"))
                .thenReturn(Optional.empty());

        mvc.perform(get("/api/users")
                        .param("email", "missing@gmail.com"))
                .andExpect(status().isNotFound());
    }

}
