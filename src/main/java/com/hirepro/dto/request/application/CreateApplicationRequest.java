// src/main/java/com/hirepro/dto/request/application/CreateApplicationRequest.java
package com.hirepro.dto.request.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateApplicationRequest {
    @NotBlank
    private String jobId;

    @NotBlank
    private String applicantName;

    @Email
    @NotBlank
    private String applicantEmail;

    private String resumeUrl;
    private String coverLetter;
}
