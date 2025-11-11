package com.demo_project_yhlas.service.impl;

import com.demo_project_yhlas.entity.User;
import com.demo_project_yhlas.repository.UserRepository;
import com.demo_project_yhlas.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User create(String email, String rawPassword){
        if (userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists " + email);
        }
        User user = User.builder()
                .email(email)
                .password(rawPassword) // later: hash
                .build();
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getById(Long id){
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
