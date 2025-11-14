package com.demo_project_yhlas.controller;

import com.demo_project_yhlas.entity.User;
import com.demo_project_yhlas.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class UserTestController {

    private final UserService userService;

    public record CreateUserRequest(
            @NotBlank @Email String email,
            @NotBlank String password
    ) {}

    public record UserResponse(
            Long id,
            String email,
            LocalDateTime createdAt
    )
        {
        public static UserResponse from(User u) {
            return new UserResponse(u.getId(), u.getEmail(), u.getCreatedAt());
        }
        }

    @PostMapping("/createUser")
    public UserResponse createUser(@RequestBody @Valid CreateUserRequest request) {
        User saved = userService.create(request.email(), request.password());
        return UserResponse.from(saved);
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    @GetMapping("/users/email/{email}")
    public UserResponse getUserByEmail(@PathVariable String email) {
        User user = userService.getByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return UserResponse.from(user);
    }
}



