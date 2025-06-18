// src/main/java/com/hirepro/util/mapper/JobMapper.java
package com.hirepro.util.mapper;

import com.hirepro.dto.response.job.JobResponse;
import com.hirepro.model.Job;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface JobMapper {
    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    JobResponse toJobResponse(Job job);
}