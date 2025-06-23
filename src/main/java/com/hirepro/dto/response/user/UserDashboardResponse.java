// src/main/java/com/hirepro/dto/response/user/UserDashboardResponse.java
package com.hirepro.dto.response.user;

public class UserDashboardResponse {
    private long totalActiveJobs;
    private long totalApplications;
    private long pendingApplications;

    public UserDashboardResponse(long totalActiveJobs, long totalApplications, long pendingApplications) {
        this.totalActiveJobs = totalActiveJobs;
        this.totalApplications = totalApplications;
        this.pendingApplications = pendingApplications;
    }

    // Getters
    public long getTotalActiveJobs() {
        return totalActiveJobs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public long getPendingApplications() {
        return pendingApplications;
    }
}