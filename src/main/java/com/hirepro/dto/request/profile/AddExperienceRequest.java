package com.hirepro.dto.request.profile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AddExperienceRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String company;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;
}
