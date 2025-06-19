// src/main/java/com/hirepro/model/Application.java
package com.hirepro.model;

import com.hirepro.model.enums.ApplicationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;

import java.time.Instant;

@Entity
public class Application {

    @Id
    private String id;

    private String jobId;

    private String userId;  // <-- Added userId to match repository

    private String applicantName;

    private String applicantEmail;

    private String resumeUrl;

    private String coverLetter;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(nullable = false, updatable = false)
    private Instant appliedAt;  // For sorting in repository query

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public Instant getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(Instant appliedAt) {
        this.appliedAt = appliedAt;
    }

    // Automatically set appliedAt before insert if not set
    @PrePersist
    protected void onCreate() {
        if (this.appliedAt == null) {
            this.appliedAt = Instant.now();
        }
    }
}
