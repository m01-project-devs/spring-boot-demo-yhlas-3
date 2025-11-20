package com.demo_project_yhlas.demo;

import com.demo_project_yhlas.controller.UserController;
import com.demo_project_yhlas.entity.User;
import com.demo_project_yhlas.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

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
    void getUserByEmail_returnsUser() throws Exception {
        User user = sampleUser();

        Mockito.when(userService.getByEmail("example@gmail.com"))
                .thenReturn(Optional.of(user));

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


    @Test
    void createUser_returns201_andBody() throws Exception {
        User saved = sampleUser();

        Mockito.when(userService.create("example@gmail.com", "123Pass"))
                .thenReturn(saved);

        mvc.perform(post("/api/users")
                        .content("""
                                {
                                   "email": "example@gmail.com",
                                   "password": "123Pass"
                                }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("example@gmail.com")));
    }


    @Test
    void getAllUsers_returnsList() throws Exception {
        User u1 = sampleUser();
        User u2 = User.builder()
                .id(2L)
                .email("second@gmail.com")
                .password("x")
                .build();

        Mockito.when(userService.getAll())
                .thenReturn(List.of(u1, u2));

        mvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email", is("example@gmail.com")))
                .andExpect(jsonPath("$[1].email", is("second@gmail.com")));
    }


    @Test
    void updatePassword_returns200() throws Exception {
        User existing = sampleUser();
        User updated = sampleUser();
        updated.setPassword("NewPassword321");

        Mockito.when(userService.getByEmail("example@gmail.com"))
                .thenReturn(Optional.of(existing));

        Mockito.when(userService.updatePassword(eq(1L), eq("NewPassword321")))
                .thenReturn(updated);

        mvc.perform(put("/api/users/password")
                        .param("email", "example@gmail.com")
                        .content("""
                            {
                              "newPassword": "NewPassword321"
                            }
                            """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("example@gmail.com")));
    }

    @Test
    void updatePassword_userNotFound_returns404() throws Exception {
        Mockito.when(userService.getByEmail("missing@gmail.com"))
                .thenReturn(Optional.empty());

        mvc.perform(put("/api/users/password")
                        .param("email", "missing@gmail.com")
                        .content("""
                            {
                              "newPassword": "NewPassword321"
                            }
                            """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteUser_returns204() throws Exception {
        User existing = sampleUser();

        Mockito.when(userService.getByEmail("example@gmail.com"))
                .thenReturn(Optional.of(existing));

        mvc.perform(delete("/api/users")
                        .param("email", "example@gmail.com"))
                .andExpect(status().isNoContent());

        Mockito.verify(userService).deleteById(1L);
    }

    @Test
    void deleteUser_notFound_returns404() throws Exception {
        Mockito.when(userService.getByEmail("missing@gmail.com"))
                .thenReturn(Optional.empty());

        mvc.perform(delete("/api/users")
                        .param("email", "missing@gmail.com"))
                .andExpect(status().isNotFound());
    }


}
