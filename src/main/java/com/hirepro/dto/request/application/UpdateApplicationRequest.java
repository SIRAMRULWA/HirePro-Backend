// src/main/java/com/hirepro/dto/request/application/UpdateApplicationRequest.java
package com.hirepro.dto.request.application;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationRequest {
    @NotBlank(message = "Cover letter cannot be blank")
    private String coverLetter;

    @NotBlank(message = "Resume URL cannot be blank")
    private String resumeUrl;
}