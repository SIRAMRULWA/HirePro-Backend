package com.hirepro.controller.auth;

import com.hirepro.dto.request.auth.LoginRequest;
import com.hirepro.dto.request.auth.RegisterRequest;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.security.JwtTokenProvider;
import com.hirepro.service.AuthService;
import com.hirepro.service.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Use AuthService for authentication
            String userRole = authService.authenticate(loginRequest);

            // Generate JWT token using email
            String jwt = tokenProvider.generateTokenFromEmail(loginRequest.getEmail());

            return ResponseEntity.ok(new JwtAuthenticationResponse(
                    true,
                    "Login successful",
                    jwt,
                    userRole,
                    tokenProvider.getJwtExpirationInMillis()
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            ApiResponse response = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Registration failed: " + ex.getMessage()));
        }
    }

    @Data
    public static class JwtAuthenticationResponse extends ApiResponse {
        private final String token;
        private final String role;
        private final long expiresIn;

        public JwtAuthenticationResponse(boolean success, String message,
                                         String token, String role, long expiresIn) {
            super(success, message);
            this.token = token;
            this.role = role;
            this.expiresIn = expiresIn;
        }
    }
}