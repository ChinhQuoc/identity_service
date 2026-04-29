package com.example.identity_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.entity.User;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        User user = new User();

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(UserUpdateRequest user, String userId) {
        User existingUser = getUserById(userId);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        existingUser.setPassword(user.getPassword());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setDob(user.getDob());

        return userRepository.save(existingUser);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}