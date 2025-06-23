package com.hirepro.dto.response.admin;

public class DashboardStatsResponse {
    private long totalUsers;
    private long totalJobs;
    private long totalApplications;
    private long pendingApplications;

    public DashboardStatsResponse(long totalUsers, long totalJobs, long totalApplications, long pendingApplications) {
        this.totalUsers = totalUsers;
        this.totalJobs = totalJobs;
        this.totalApplications = totalApplications;
        this.pendingApplications = pendingApplications;
    }

    // Getters
    public long getTotalUsers() { return totalUsers; }
    public long getTotalJobs() { return totalJobs; }
    public long getTotalApplications() { return totalApplications; }
    public long getPendingApplications() { return pendingApplications; }
}

