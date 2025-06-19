package com.hirepro.dto.response.user;

import com.hirepro.model.enums.Role;
import com.hirepro.model.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private boolean emailVerified;
}