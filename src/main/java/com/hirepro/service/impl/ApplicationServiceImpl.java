package com.hirepro.service.impl;

import com.hirepro.dto.mapper.ApplicationMapper;
import com.hirepro.dto.request.application.CreateApplicationRequest;
import com.hirepro.dto.request.application.UpdateApplicationRequest;
import com.hirepro.dto.response.application.ApplicationResponse;
import com.hirepro.exception.ResourceNotFoundException;
import com.hirepro.model.Application;
import com.hirepro.model.enums.ApplicationStatus;
import com.hirepro.repository.ApplicationRepository;
import com.hirepro.service.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  ApplicationMapper applicationMapper) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
    }

    @Override
    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAll()
                .stream()
                .map(applicationMapper::toApplicationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponse> getApplicationsByJobId(String jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(applicationMapper::toApplicationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponse getApplicationById(String id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));
        return applicationMapper.toApplicationResponse(application);
    }

    @Override
    public ApplicationResponse updateApplicationStatus(String id, String status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        application.setStatus(ApplicationStatus.valueOf(status.toUpperCase()));
        Application updatedApplication = applicationRepository.save(application);

        return applicationMapper.toApplicationResponse(updatedApplication);
    }

    @Override
    public ApplicationResponse updateApplication(String id, UpdateApplicationRequest request) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        application.setCoverLetter(request.getCoverLetter());
        application.setResumeUrl(request.getResumeUrl());

        Application updatedApplication = applicationRepository.save(application);
        return applicationMapper.toApplicationResponse(updatedApplication);
    }

    @Override
    public ApplicationResponse createApplication(CreateApplicationRequest request, String userId) {
        Application application = new Application();
        application.setId(UUID.randomUUID().toString());
        application.setJobId(request.getJobId());
        application.setApplicantName(request.getApplicantName());
        application.setApplicantEmail(request.getApplicantEmail());
        application.setResumeUrl(request.getResumeUrl());
        application.setCoverLetter(request.getCoverLetter());
        application.setUserId(userId);
        application.setAppliedAt(Instant.now());
        application.setStatus(ApplicationStatus.PENDING);

        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.toApplicationResponse(savedApplication);
    }

    // ------------------ USER-SPECIFIC METHODS ------------------

    @Override
    public List<ApplicationResponse> getApplicationsByUserId(String userId) {
        return applicationRepository.findByUserId(userId)
                .stream()
                .map(applicationMapper::toApplicationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponse getApplicationByIdForUser(String id, String userId) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        if (!application.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Application", "userId", userId);
        }

        return applicationMapper.toApplicationResponse(application);
    }

    @Override
    public ApplicationResponse updateApplicationForUser(String id, UpdateApplicationRequest request, String userId) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        if (!application.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Application", "userId", userId);
        }

        application.setCoverLetter(request.getCoverLetter());
        application.setResumeUrl(request.getResumeUrl());

        Application updatedApplication = applicationRepository.save(application);
        return applicationMapper.toApplicationResponse(updatedApplication);
    }

    @Override
    public void deleteApplicationForUser(String id, String userId) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        if (!application.getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Application", "userId", userId);
        }

        applicationRepository.delete(application);
    }
}
