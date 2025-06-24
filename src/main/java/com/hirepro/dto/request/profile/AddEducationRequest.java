package com.hirepro.dto.request.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddEducationRequest {
    @NotBlank
    private String institution;

    @NotBlank
    private String degree;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;
}
