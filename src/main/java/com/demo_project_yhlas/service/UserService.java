package com.demo_project_yhlas.service;

import com.demo_project_yhlas.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(String email, String rawPassword);

    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    List<User> getAll();

    User updatePassword(Long id, String newPassword);

    void deleteById(Long id);
}
