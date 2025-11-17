package com.demo_project_yhlas.controller;

import com.demo_project_yhlas.dto.response.UserResponse;
import com.demo_project_yhlas.entity.User;
import com.demo_project_yhlas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponse> getUserById(@RequestParam String email){
        return userService.getByEmail(email)
                .map(user -> ResponseEntity.ok(UserResponse.from(user)))
                .orElse(ResponseEntity.notFound().build());

    }
}
