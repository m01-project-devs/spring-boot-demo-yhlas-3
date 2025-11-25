package com.demo_project_yhlas.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
    @NotBlank String newPassword
) {}
