package com.demo_project_yhlas.controller;

import com.demo_project_yhlas.dto.request.UpdatePasswordRequest;
import com.demo_project_yhlas.dto.request.UserRequest;
import com.demo_project_yhlas.dto.response.UserResponse;
import com.demo_project_yhlas.entity.User;
import com.demo_project_yhlas.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam String email){
        return userService.getByEmail(email)
                .map(user -> ResponseEntity.ok(UserResponse.from(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody UserRequest request){
        User saved = userService.create(request.email(), request.password());
        return UserResponse.from(saved);
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers(){
        return userService.getAll().stream().map(UserResponse::from).toList();
    }

    @PutMapping("/password")
    public ResponseEntity<UserResponse> updatePassword(@RequestParam String email,
                                                       @RequestBody UpdatePasswordRequest req){
        return userService.getByEmail(email)
                .map(user -> {
                    User updated = userService.updatePassword(user.getId(), req.newPassword());
                    return ResponseEntity.ok(UserResponse.from(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam String email) {
        return userService.getByEmail(email)
                .map(user -> {
                    userService.deleteById(user.getId());
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
