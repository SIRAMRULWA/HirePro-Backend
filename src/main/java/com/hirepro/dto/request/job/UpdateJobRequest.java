// src/main/java/com/hirepro/dto/request/job/UpdateJobRequest.java
package com.hirepro.dto.request.job;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateJobRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String requirements;

    @NotBlank
    private String location;

    @NotBlank
    private String salaryRange;

    private LocalDateTime closingDate;
}