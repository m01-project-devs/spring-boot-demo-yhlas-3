package com.demo_project_yhlas.dto.response;

import com.demo_project_yhlas.entity.User;

public record UserResponse(String email) {
    public static UserResponse from(User user){
        return new UserResponse(
                user.getEmail()
        );
    }
}
