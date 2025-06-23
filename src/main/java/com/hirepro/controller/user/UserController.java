// src/main/java/com/hirepro/controller/user/UserController.java
package com.hirepro.controller.user;

import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.user.UserDashboardResponse;
import com.hirepro.model.enums.ApplicationStatus;
import com.hirepro.model.enums.JobStatus;
import com.hirepro.repository.ApplicationRepository;
import com.hirepro.repository.JobRepository;
import com.hirepro.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/dashboard")
@PreAuthorize("hasRole('USER')")
public class UserController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping
    public ResponseEntity<ApiResponse> getUserDashboardStats(@AuthenticationPrincipal UserPrincipal currentUser) {
        // Convert user ID to String since your repositories expect String IDs
        String userId = currentUser.getId().toString();

        // Count active jobs (using JobStatus.ACTIVE)
        long totalActiveJobs = jobRepository.countByStatus(JobStatus.ACTIVE);

        // Count user applications
        long totalApplications = applicationRepository.countByUserId(userId);

        // Count pending applications
        long pendingApplications = applicationRepository.findByUserIdAndStatus(
                userId,
                ApplicationStatus.PENDING
        ).size(); // Using size() since there's no direct count method

        UserDashboardResponse stats = new UserDashboardResponse(
                totalActiveJobs,
                totalApplications,
                pendingApplications
        );

        return ResponseEntity.ok(new ApiResponse(true, "User dashboard stats fetched successfully", stats));
    }
}