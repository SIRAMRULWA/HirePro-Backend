package com.hirepro.service;

import com.hirepro.dto.request.auth.RegisterRequest;
import com.hirepro.dto.request.profile.*;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.user.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    // User management methods
    Page<UserResponse> getAllUsers(String search, String status, int page, int size, String[] sort);
    UserResponse getUserById(Long id);
    ApiResponse updateUserStatus(Long id, String status);
    ApiResponse deleteUser(Long id);

    // Authentication methods
    ApiResponse registerUser(RegisterRequest registerRequest);
    String authenticate(String email, String password) throws Exception;
    boolean existsByEmail(String email);

    // Profile management methods
    UserResponse updateUserProfile(Long userId, UpdateProfileRequest request);
    ApiResponse changePassword(Long userId, String currentPassword, String newPassword);
    UserResponse uploadProfilePicture(Long userId, MultipartFile file);
    UserResponse addSkill(Long userId, AddSkillRequest request);
    UserResponse removeSkill(Long userId, RemoveSkillRequest request);
    UserResponse addExperience(Long userId, AddExperienceRequest request);
    UserResponse removeExperience(Long userId, String experienceId);
    UserResponse addEducation(Long userId, AddEducationRequest request);
    UserResponse removeEducation(Long userId, String educationId);
}
