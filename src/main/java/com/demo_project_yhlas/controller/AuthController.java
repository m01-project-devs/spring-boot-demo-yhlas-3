package com.demo_project_yhlas.controller;

import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        return "login endpoint called for user: " + body.get("email");
    }
}
