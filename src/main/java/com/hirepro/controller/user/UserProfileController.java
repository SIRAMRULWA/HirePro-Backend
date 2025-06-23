// src/main/java/com/hirepro/controller/user/UserProfileController.java
package com.hirepro.controller.user;

import com.hirepro.dto.request.profile.UpdateProfileRequest;
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
}