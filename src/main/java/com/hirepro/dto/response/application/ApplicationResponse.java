// src/main/java/com/hirepro/dto/response/application/ApplicationResponse.java
package com.hirepro.dto.response.application;

import com.hirepro.model.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationResponse {
    private String id;
    private String jobId;
    private String userId;
    private String coverLetter;
    private String resumeUrl;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
}