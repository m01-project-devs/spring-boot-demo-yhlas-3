package com.demo_project_yhlas.demo;

import com.demo_project_yhlas.controller.UserController;
import com.demo_project_yhlas.entity.User;
import com.demo_project_yhlas.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    @MockBean
    private UserService userService;

    @Test
    void getUserById_returnsUser() throws Exception{
        User example = User.builder().id(1L).email("example@gmail.com").password("123Pass").build();

        Mockito.when(userService.getById(1L)).thenReturn(Optional.of(example));

        mvc.perform(get("/api/users/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("example@gmail.com")));
    }

    @Test
    void getUserById_notFound_returns404() throws Exception{
        Mockito.when(userService.getById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/users/99")).andExpect(status().isNotFound());
    }




}
