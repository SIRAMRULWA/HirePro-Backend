package com.hirepro.service;

import com.hirepro.dto.request.application.CreateApplicationRequest;
import com.hirepro.dto.request.application.UpdateApplicationRequest;
import com.hirepro.dto.response.application.ApplicationResponse;

import java.util.List;

public interface ApplicationService {

    // Admin methods
    List<ApplicationResponse> getAllApplications();

    List<ApplicationResponse> getApplicationsByJobId(String jobId);

    ApplicationResponse getApplicationById(String id);

    ApplicationResponse updateApplicationStatus(String id, String status);

    ApplicationResponse updateApplication(String id, UpdateApplicationRequest request);

    // User methods
    ApplicationResponse createApplication(CreateApplicationRequest request, String userId);

    List<ApplicationResponse> getApplicationsByUserId(String userId);

    ApplicationResponse getApplicationByIdForUser(String id, String userId);

    ApplicationResponse updateApplicationForUser(String id, UpdateApplicationRequest request, String userId);

    void deleteApplicationForUser(String id, String userId);


}
