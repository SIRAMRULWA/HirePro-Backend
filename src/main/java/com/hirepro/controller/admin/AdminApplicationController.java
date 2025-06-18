// src/main/java/com/hirepro/controller/admin/AdminApplicationController.java
package com.hirepro.controller.admin;

import com.hirepro.dto.request.application.UpdateApplicationRequest;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.application.ApplicationResponse;
import com.hirepro.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/applications")
@PreAuthorize("hasRole('ADMIN')")
public class AdminApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByJobId(@PathVariable String jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsByJobId(jobId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(id, status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(
            @PathVariable String id,
            @Valid @RequestBody UpdateApplicationRequest request) {
        return ResponseEntity.ok(applicationService.updateApplication(id, request));
    }
}