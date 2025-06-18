package com.hirepro.dto.response.job;

import com.hirepro.model.enums.JobStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JobResponse {
    private String id;
    private String title;
    private String description;
    private String requirements;
    private String location;
    private String salaryRange;
    private JobStatus status;
    private LocalDateTime postedDate;
    private LocalDateTime closingDate;
    private LocalDateTime updatedAt;
}