package com.demo_project_yhlas.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
    @NotBlank(message = "newPassword must not be blank")
    @Size(min = 6, max = 64, message = "newPassword must be between 6 and 64 characters")
    String newPassword
) {}
