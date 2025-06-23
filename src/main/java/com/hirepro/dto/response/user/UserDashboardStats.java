package com.hirepro.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardStats {
    private int totalApplications;
    private int pendingApplications;
    private int interviewsScheduled;
    private int activeJobs;
    private int profileCompletion;
}