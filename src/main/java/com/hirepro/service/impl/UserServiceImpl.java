package com.hirepro.service.impl;

import com.hirepro.dto.mapper.UserMapper;
import com.hirepro.dto.request.auth.RegisterRequest;
import com.hirepro.dto.request.profile.*;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.user.UserResponse;
import com.hirepro.exception.ResourceNotFoundException;
import com.hirepro.model.*;
import com.hirepro.model.enums.Role;
import com.hirepro.model.enums.UserStatus;
import com.hirepro.repository.*;
import com.hirepro.security.SecurityUtils;
import com.hirepro.service.AuditLogService;
import com.hirepro.service.UserService;
import com.hirepro.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private UserSkillRepository userSkillRepository;
    @Autowired private WorkExperienceRepository workExperienceRepository;
    @Autowired private EducationRepository educationRepository;
    @Autowired private UserMapper userMapper;
    @Autowired private AuditLogService auditLogService;
    @Autowired private PasswordEncoder passwordEncoder;

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
                "STATUS_UPDATE", "USER", id.toString(),
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
                "DELETE", "USER", id.toString(),
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
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);

        userRepository.save(user);

        auditLogService.logAction(
                "REGISTER", "USER", user.getId().toString(),
                "SYSTEM", "New user registered"
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

        user.setName(request.getName());
        user.setEmail(request.getEmail());

        if (request.getPhone() != null) {
            user.setPhone(request.getPhone());
        }

        User updatedUser = userRepository.save(user);

        auditLogService.logAction(
                "PROFILE_UPDATE", "USER", userId.toString(),
                SecurityUtils.getCurrentUserId(), "User profile updated"
        );

        return userMapper.toUserResponse(updatedUser);
    }

    @Override
    public ApiResponse changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return new ApiResponse(false, "Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        auditLogService.logAction(
                "PASSWORD_CHANGE", "USER", userId.toString(),
                SecurityUtils.getCurrentUserId(), "Password changed"
        );

        return new ApiResponse(true, "Password updated successfully");
    }

    @Override
    public UserResponse uploadProfilePicture(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        String fakeUrl = "/uploads/" + file.getOriginalFilename(); // Replace with actual storage
        user.setProfilePictureUrl(fakeUrl);
        userRepository.save(user);

        auditLogService.logAction(
                "PICTURE_UPLOAD", "USER", userId.toString(),
                SecurityUtils.getCurrentUserId(), "Profile picture uploaded"
        );

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse addSkill(Long userId, AddSkillRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserSkill skill = new UserSkill();
        skill.setName(request.getName());
        skill.setUser(user);
        userSkillRepository.save(skill);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse removeSkill(Long userId, RemoveSkillRequest request) {
        userSkillRepository.deleteById(request.getSkillId());
        return getUserById(userId);
    }

    @Override
    public UserResponse addExperience(Long userId, AddExperienceRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        WorkExperience exp = new WorkExperience();
        exp.setUser(user);
        exp.setTitle(request.getTitle());
        exp.setCompany(request.getCompany());
        exp.setStartDate(request.getStartDate());
        exp.setEndDate(request.getEndDate());
        workExperienceRepository.save(exp);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse removeExperience(Long userId, String experienceId) {
        workExperienceRepository.deleteById(experienceId);
        return getUserById(userId);
    }

    @Override
    public UserResponse addEducation(Long userId, AddEducationRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Education edu = new Education();
        edu.setUser(user);
        edu.setInstitution(request.getInstitution());
        edu.setDegree(request.getDegree());
        edu.setStartDate(request.getStartDate());
        edu.setEndDate(request.getEndDate());
        educationRepository.save(edu);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse removeEducation(Long userId, String educationId) {
        educationRepository.deleteById(educationId);
        return getUserById(userId);
    }
}
