package com.hirepro.dto.request.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateJobRequest {
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

    @NotNull
    private LocalDateTime closingDate;
}