package com.hirepro.controller.user;

import com.hirepro.dto.request.profile.*;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.user.UserResponse;
import com.hirepro.security.UserPrincipal;
import com.hirepro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user/profile")
@PreAuthorize("hasRole('USER')")
public class UserProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(userService.getUserById(currentUser.getId()));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateProfile(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody UpdateProfileRequest request) {
        return ResponseEntity.ok(userService.updateUserProfile(currentUser.getId(), request));
    }

    @PatchMapping("/password")
    public ResponseEntity<ApiResponse> changePassword(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        return ResponseEntity.ok(userService.changePassword(
                currentUser.getId(),
                currentPassword,
                newPassword
        ));
    }

    @PostMapping("/picture")
    public ResponseEntity<UserResponse> uploadProfilePicture(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(userService.uploadProfilePicture(currentUser.getId(), file));
    }

    @PostMapping("/skills")
    public ResponseEntity<UserResponse> addSkill(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody AddSkillRequest request) {
        return ResponseEntity.ok(userService.addSkill(currentUser.getId(), request));
    }

    @DeleteMapping("/skills")
    public ResponseEntity<UserResponse> removeSkill(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody RemoveSkillRequest request) {
        return ResponseEntity.ok(userService.removeSkill(currentUser.getId(), request));
    }

    @PostMapping("/experience")
    public ResponseEntity<UserResponse> addExperience(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody AddExperienceRequest request) {
        return ResponseEntity.ok(userService.addExperience(currentUser.getId(), request));
    }

    @DeleteMapping("/experience/{experienceId}")
    public ResponseEntity<UserResponse> removeExperience(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable String experienceId) {
        return ResponseEntity.ok(userService.removeExperience(currentUser.getId(), experienceId));
    }

    @PostMapping("/education")
    public ResponseEntity<UserResponse> addEducation(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @Valid @RequestBody AddEducationRequest request) {
        return ResponseEntity.ok(userService.addEducation(currentUser.getId(), request));
    }

    @DeleteMapping("/education/{educationId}")
    public ResponseEntity<UserResponse> removeEducation(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @PathVariable String educationId) {
        return ResponseEntity.ok(userService.removeEducation(currentUser.getId(), educationId));
    }
}