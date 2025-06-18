package com.hirepro.controller.admin;

import com.hirepro.dto.request.job.CreateJobRequest;
import com.hirepro.dto.request.job.UpdateJobRequest;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.job.JobResponse;
import com.hirepro.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/jobs")
@PreAuthorize("hasRole('ADMIN')")
public class AdminJobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody CreateJobRequest request) {
        return ResponseEntity.ok(jobService.createJob(request));
    }

    @GetMapping
    public ResponseEntity<Page<JobResponse>> getAllJobs(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title,asc") String[] sort) {
        return ResponseEntity.ok(jobService.getAllJobs(search, status, page, size, sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable String id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> updateJob(
            @PathVariable String id,
            @Valid @RequestBody UpdateJobRequest request) {
        return ResponseEntity.ok(jobService.updateJob(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable String id) {
        return ResponseEntity.ok(jobService.deleteJob(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<JobResponse> updateJobStatus(
            @PathVariable String id,
            @RequestParam String status) {
        return ResponseEntity.ok(jobService.updateJobStatus(id, status));
    }
}