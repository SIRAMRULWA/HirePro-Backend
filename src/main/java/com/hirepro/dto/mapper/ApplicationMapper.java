package com.hirepro.dto.mapper;

import com.hirepro.dto.response.application.ApplicationResponse;
import com.hirepro.model.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDateTime.class, ZoneId.class}
)
public interface ApplicationMapper {

    @Mapping(target = "appliedAt", source = "appliedAt", qualifiedByName = "instantToLocalDateTime")
    ApplicationResponse toApplicationResponse(Application application);

    @Named("instantToLocalDateTime")
    default LocalDateTime instantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}