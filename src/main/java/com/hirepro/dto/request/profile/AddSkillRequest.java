package com.hirepro.dto.request.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddSkillRequest {
    @NotBlank
    private String name;
}
