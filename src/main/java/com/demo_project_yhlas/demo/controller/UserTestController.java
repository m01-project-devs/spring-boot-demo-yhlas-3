package com.demo_project_yhlas.demo.controller;

import com.demo_project_yhlas.entity.User;
import com.demo_project_yhlas.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class UserTestController {

    private final UserRepository userRepository;

    public record CreateUserRequest(
        @NotBlank @Email String email,
        @NotBlank String password
    ) {}

    @PostMapping("/users")
    public User createUser(@RequestBody CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())){
            throw new IllegalArgumentException("Email already exists");
        }
        User user = User.builder()
                .email(request.email())
                .password(request.password())
                .build();
        return userRepository.save(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
