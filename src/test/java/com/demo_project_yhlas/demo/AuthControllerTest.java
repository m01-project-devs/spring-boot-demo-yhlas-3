package com.demo_project_yhlas.demo;

import com.demo_project_yhlas.config.SecurityConfig;
import com.demo_project_yhlas.controller.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void login_isPublic_returns200() throws Exception {
        mvc.perform(post("/api/auth/login")
                        .content("""
                            {"email": "test@example.com"}
                        """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
