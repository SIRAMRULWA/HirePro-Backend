// src/main/java/com/hirepro/dto/mapper/ApplicationMapper.java
package com.hirepro.dto.mapper;

import com.hirepro.dto.response.application.ApplicationResponse;
import com.hirepro.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ApplicationMapper {
    ApplicationResponse toApplicationResponse(Application application);
}