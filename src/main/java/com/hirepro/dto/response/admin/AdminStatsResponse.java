package com.hirepro.dto.response.admin;

public class AdminStatsResponse {
    private long totalUsers;
    private long totalJobs;
    private long totalApplications;

    public AdminStatsResponse(long totalUsers, long totalJobs, long totalApplications) {
        this.totalUsers = totalUsers;
        this.totalJobs = totalJobs;
        this.totalApplications = totalApplications;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public long getTotalJobs() {
        return totalJobs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public void setTotalJobs(long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }
}

