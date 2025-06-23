// src/main/java/com/hirepro/service/JobService.java
package com.hirepro.service;

import com.hirepro.dto.request.job.CreateJobRequest;
import com.hirepro.dto.request.job.UpdateJobRequest;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.application.ApplicationResponse;
import com.hirepro.dto.response.job.JobResponse;
import org.springframework.data.domain.Page;

public interface JobService {
    // Existing methods
    JobResponse createJob(CreateJobRequest request);
    Page<JobResponse> getAllJobs(String search, String status, int page, int size, String[] sort);
    JobResponse getJobById(String id);
    JobResponse updateJob(String id, UpdateJobRequest request);
    ApiResponse deleteJob(String id);
    JobResponse updateJobStatus(String id, String status);

    // New methods for user job operations
    ApiResponse applyForJob(String jobId, String userId);
    Page<ApplicationResponse> getUserApplications(String userId, int page, int size);
}