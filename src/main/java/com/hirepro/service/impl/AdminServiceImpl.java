package com.hirepro.service.impl;

import com.hirepro.dto.response.ApiResponse;
import com.hirepro.dto.response.admin.AdminStatsResponse;
import com.hirepro.repository.ApplicationRepository;
import com.hirepro.repository.JobRepository;
import com.hirepro.repository.UserRepository;
import com.hirepro.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public ApiResponse getSystemStats() {
        long totalUsers = userRepository.count();
        long totalJobs = jobRepository.count();
        long totalApplications = applicationRepository.count();

        AdminStatsResponse stats = new AdminStatsResponse(totalUsers, totalJobs, totalApplications);

        return new ApiResponse(true, "System statistics fetched successfully", stats);
    }
}
