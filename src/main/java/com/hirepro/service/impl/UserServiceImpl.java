package com.hirepro.service.impl;

import com.hirepro.dto.request.auth.RegisterRequest;
import com.hirepro.dto.request.profile.UpdateProfileRequest;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.user.UserResponse;
import com.hirepro.exception.ResourceNotFoundException;
import com.hirepro.model.User;
import com.hirepro.model.enums.Role;
import com.hirepro.model.enums.UserStatus;
import com.hirepro.repository.UserRepository;
import com.hirepro.security.SecurityUtils;
import com.hirepro.service.AuditLogService;
import com.hirepro.service.UserService;
import com.hirepro.util.PaginationUtil;
import com.hirepro.dto.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private PasswordEncoder passwordEncoder;  // For encoding user passwords

    @Override
    public Page<UserResponse> getAllUsers(String search, String status, int page, int size, String[] sort) {
        Pageable pageable = PaginationUtil.getPageable(page, size, sort);

        if (search != null && !search.isEmpty()) {
            return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                    search, search, pageable).map(userMapper::toUserResponse);
        } else if (status != null && !status.isEmpty()) {
            return userRepository.findByStatus(UserStatus.valueOf(status.toUpperCase()), pageable)
                    .map(userMapper::toUserResponse);
        }
        return userRepository.findAll(pageable).map(userMapper::toUserResponse);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return userMapper.toUserResponse(user);
    }

    @Override
    public ApiResponse updateUserStatus(Long id, String status) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        UserStatus newStatus = UserStatus.valueOf(status.toUpperCase());
        String oldStatus = user.getStatus().name();
        user.setStatus(newStatus);
        userRepository.save(user);

        auditLogService.logAction(
                "STATUS_UPDATE",
                "USER",
                id.toString(),
                SecurityUtils.getCurrentUserId(),
                String.format("Status changed from %s to %s", oldStatus, newStatus)
        );

        return new ApiResponse(true, "User status updated successfully");
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public ApiResponse deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepository.delete(user);

        auditLogService.logAction(
                "DELETE",
                "USER",
                id.toString(),
                SecurityUtils.getCurrentUserId(),
                "User deleted"
        );

        return new ApiResponse(true, "User deleted successfully");
    }

    @Override
    public ApiResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new ApiResponse(false, "Email is already in use");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER); // default role
        user.setStatus(UserStatus.ACTIVE); // default status

        userRepository.save(user);

        auditLogService.logAction(
                "REGISTER",
                "USER",
                user.getId().toString(),
                "SYSTEM",  // Changed from null to "SYSTEM" to avoid DB error
                "New user registered"
        );

        return new ApiResponse(true, "User registered successfully");
    }

    @Override
    public String authenticate(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Invalid email or password");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new Exception("User is not active");
        }

        return user.getRole().name();
    }

    @Override
    public UserResponse updateUserProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Update user details from the request
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Update phone if it exists in the request
        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        // Save the updated user
        User updatedUser = userRepository.save(user);

        // Log the action
        auditLogService.logAction(
                "PROFILE_UPDATE",
                "USER",
                userId.toString(),
                SecurityUtils.getCurrentUserId(),
                "User profile updated"
        );

        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public ApiResponse changePassword(Long userId, String currentPassword, String newPassword) {
        // TODO: Implement password change functionality
        throw new UnsupportedOperationException("Not implemented yet");
    }
}