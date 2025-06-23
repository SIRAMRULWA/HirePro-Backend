// src/main/java/com/hirepro/controller/user/UserJobController.java
package com.hirepro.controller.user;

import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.application.ApplicationResponse;
import com.hirepro.dto.response.job.JobResponse;
import com.hirepro.security.UserPrincipal;
import com.hirepro.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/jobs")
@PreAuthorize("hasRole('USER')")
public class UserJobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<Page<JobResponse>> getActiveJobs(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title,asc") String[] sort) {
        return ResponseEntity.ok(jobService.getAllJobs(search, "ACTIVE", page, size, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable String id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<ApiResponse> applyForJob(
            @PathVariable String id,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(jobService.applyForJob(id, currentUser.getId().toString()));
    }

    @GetMapping("/applications")
    public ResponseEntity<Page<ApplicationResponse>> getUserApplications(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(jobService.getUserApplications(currentUser.getId().toString(), page, size));
    }
}