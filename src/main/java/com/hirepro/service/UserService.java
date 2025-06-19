package com.hirepro.service;

import com.hirepro.dto.request.auth.RegisterRequest;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.user.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<UserResponse> getAllUsers(String search, String status, int page, int size, String[] sort);
    UserResponse getUserById(Long id);
    ApiResponse updateUserStatus(Long id, String status);
    ApiResponse deleteUser(Long id);

    // Register a new user, return success/failure response
    ApiResponse registerUser(RegisterRequest registerRequest);

    // Authenticate user and return role string if successful, else null or throw exception
    String authenticate(String email, String password) throws Exception;

    boolean existsByEmail(String email);
}
