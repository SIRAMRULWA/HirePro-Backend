package com.hirepro.service.impl;

import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.user.UserResponse;
import com.hirepro.exception.ResourceNotFoundException;
import com.hirepro.model.User;
import com.hirepro.model.enums.UserStatus;
import com.hirepro.repository.UserRepository;
import com.hirepro.security.SecurityUtils;
import com.hirepro.service.AuditLogService;
import com.hirepro.service.UserService;
import com.hirepro.util.PaginationUtil;
import com.hirepro.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuditLogService auditLogService;

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
}