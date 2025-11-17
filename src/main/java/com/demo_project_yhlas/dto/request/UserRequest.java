package com.demo_project_yhlas.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest (
        @NotBlank @Email String email,
        @NotBlank String password
) {}

