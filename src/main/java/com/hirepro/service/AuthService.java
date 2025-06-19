package com.hirepro.service;

import com.hirepro.dto.request.auth.LoginRequest;
import com.hirepro.dto.request.auth.RegisterRequest;
import com.hirepro.dto.response.ApiResponse;

public interface AuthService {
    // Authenticate user and return JWT token if successful
    String authenticate(LoginRequest loginRequest) throws Exception;

    // Register a new user
    ApiResponse register(RegisterRequest registerRequest) throws Exception;
}
