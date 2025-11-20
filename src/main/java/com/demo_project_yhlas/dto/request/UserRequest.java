package com.demo_project_yhlas.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest (
        @NotBlank(message = "email must not be blank")
        @Email(message = "email must be valid")
        String email,
        @NotBlank(message = "password must not be blank")
        @Size(min = 6, max = 64, message = "password must be between 6 and 64 characters")
        String password
) {}

