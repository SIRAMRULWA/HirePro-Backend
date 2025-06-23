// src/main/java/com/hirepro/dto/response/user/UserResponse.java
package com.hirepro.dto.response.user;

import com.hirepro.model.enums.Role;
import com.hirepro.model.enums.UserStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String profilePictureUrl;
    private String jobTitle;
    private String location;
    private String bio;
    private List<String> skills;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdAt;
    private boolean emailVerified;
    private int profileCompletion;
}