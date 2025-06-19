package com.hirepro.service.impl;

import com.hirepro.dto.request.auth.LoginRequest;
import com.hirepro.dto.request.auth.RegisterRequest;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.model.User;
import com.hirepro.model.enums.Role;
import com.hirepro.model.enums.UserStatus;
import com.hirepro.repository.UserRepository;
import com.hirepro.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public String authenticate(LoginRequest loginRequest) throws Exception {
        try {
            // Use Spring Security's authentication manager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new Exception("User not found"));

            if (!user.getStatus().equals(UserStatus.ACTIVE)) {
                throw new Exception("User is not active");
            }

            return user.getRole().name();
        } catch (AuthenticationException e) {
            throw new Exception("Invalid email or password");
        }
    }

    @Override
    public ApiResponse register(RegisterRequest registerRequest) throws Exception {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new Exception("Email is already registered");
        }

        // Create user using regular constructor/setter approach
        User newUser = new User();
        newUser.setName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(Role.USER);
        newUser.setStatus(UserStatus.ACTIVE);

        userRepository.save(newUser);

        return new ApiResponse(true, "User registered successfully");
    }
}