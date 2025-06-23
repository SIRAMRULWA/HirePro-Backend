// src/main/java/com/hirepro/service/impl/JobServiceImpl.java
package com.hirepro.service.impl;

import com.hirepro.dto.request.job.CreateJobRequest;
import com.hirepro.dto.request.job.UpdateJobRequest;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.application.ApplicationResponse;
import com.hirepro.dto.response.job.JobResponse;
import com.hirepro.exception.ResourceNotFoundException;
import com.hirepro.model.Application;
import com.hirepro.model.Job;
import com.hirepro.model.enums.ApplicationStatus;
import com.hirepro.model.enums.JobStatus;
import com.hirepro.repository.ApplicationRepository;
import com.hirepro.repository.JobRepository;
import com.hirepro.security.SecurityUtils;
import com.hirepro.service.AuditLogService;
import com.hirepro.service.JobService;
import com.hirepro.util.PaginationUtil;
import com.hirepro.dto.mapper.ApplicationMapper;
import com.hirepro.util.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public JobResponse createJob(CreateJobRequest request) {
        Job job = new Job();
        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirements(request.getRequirements());
        job.setLocation(request.getLocation());
        job.setSalaryRange(request.getSalaryRange());
        job.setStatus(JobStatus.OPEN);
        job.setPostedDate(LocalDateTime.now());
        job.setClosingDate(request.getClosingDate());

        Job savedJob = jobRepository.save(job);

        auditLogService.logAction(
                "CREATE",
                "JOB",
                savedJob.getId(),
                SecurityUtils.getCurrentUserId(),
                "Job created: " + savedJob.getTitle()
        );

        return jobMapper.toJobResponse(savedJob);
    }

    @Override
    public Page<JobResponse> getAllJobs(String search, String status, int page, int size, String[] sort) {
        Pageable pageable = PaginationUtil.getPageable(page, size, sort);

        if (search != null && !search.isEmpty()) {
            return jobRepository.findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
                    search, search, pageable).map(jobMapper::toJobResponse);
        } else if (status != null && !status.isEmpty()) {
            return jobRepository.findByStatus(JobStatus.valueOf(status.toUpperCase()), pageable)
                    .map(jobMapper::toJobResponse);
        }
        return jobRepository.findAll(pageable).map(jobMapper::toJobResponse);
    }

    @Override
    public JobResponse getJobById(String id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));
        return jobMapper.toJobResponse(job);
    }

    @Override
    public JobResponse updateJob(String id, UpdateJobRequest request) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setRequirements(request.getRequirements());
        job.setLocation(request.getLocation());
        job.setSalaryRange(request.getSalaryRange());
        job.setClosingDate(request.getClosingDate());

        Job updatedJob = jobRepository.save(job);

        auditLogService.logAction(
                "UPDATE",
                "JOB",
                id,
                SecurityUtils.getCurrentUserId(),
                "Job details updated"
        );

        return jobMapper.toJobResponse(updatedJob);
    }

    @Override
    public ApiResponse deleteJob(String id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));

        jobRepository.delete(job);

        auditLogService.logAction(
                "DELETE",
                "JOB",
                id,
                SecurityUtils.getCurrentUserId(),
                "Job deleted"
        );

        return new ApiResponse(true, "Job deleted successfully");
    }

    @Override
    public JobResponse updateJobStatus(String id, String status) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", id));

        JobStatus newStatus = JobStatus.valueOf(status.toUpperCase());
        String oldStatus = job.getStatus().name();
        job.setStatus(newStatus);
        Job updatedJob = jobRepository.save(job);

        auditLogService.logAction(
                "STATUS_UPDATE",
                "JOB",
                id,
                SecurityUtils.getCurrentUserId(),
                String.format("Status changed from %s to %s", oldStatus, newStatus)
        );

        return jobMapper.toJobResponse(updatedJob);
    }

    @Override
    @Transactional
    public ApiResponse applyForJob(String jobId, String userId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", jobId));

        if (job.getStatus() != JobStatus.ACTIVE) {
            return new ApiResponse(false, "Cannot apply for an inactive job");
        }

        if (applicationRepository.existsByJobIdAndUserId(jobId, userId)) {
            return new ApiResponse(false, "You have already applied for this job");
        }

        Application application = new Application();
        application.setJobId(jobId);
        application.setUserId(userId);
        application.setStatus(ApplicationStatus.PENDING);
        application.setAppliedAt(Instant.now());

        applicationRepository.save(application);

        auditLogService.logAction(
                "APPLY",
                "JOB",
                jobId,
                userId,
                "Applied for job: " + job.getTitle()
        );

        return new ApiResponse(true, "Successfully applied for the job");
    }

    @Override
    public Page<ApplicationResponse> getUserApplications(String userId, int page, int size) {
        Pageable pageable = PaginationUtil.getPageable(page, size, new String[]{"appliedAt,desc"});
        Page<Application> applications = applicationRepository.findByUserId(userId, pageable);

        return applications.map(applicationMapper::toApplicationResponse);
    }
}