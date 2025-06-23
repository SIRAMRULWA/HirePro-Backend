// src/main/java/com/hirepro/controller/admin/AdminController.java
package com.hirepro.controller.admin;

import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.admin.DashboardStatsResponse;
import com.hirepro.model.enums.ApplicationStatus;
import com.hirepro.repository.ApplicationRepository;
import com.hirepro.repository.JobRepository;
import com.hirepro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping
    public ResponseEntity<ApiResponse> getDashboardStats() {
        long totalUsers = userRepository.count();
        long totalJobs = jobRepository.count();
        long totalApplications = applicationRepository.count();
        long pendingApplications = applicationRepository.countByStatus(ApplicationStatus.PENDING);

        DashboardStatsResponse stats = new DashboardStatsResponse(
                totalUsers,
                totalJobs,
                totalApplications,
                pendingApplications
        );

        return ResponseEntity.ok(new ApiResponse(true, "Dashboard stats fetched successfully", stats));
    }
}
