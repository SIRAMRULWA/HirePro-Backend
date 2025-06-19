package com.hirepro.config;

import com.hirepro.dto.mapper.ApplicationMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public ApplicationMapper applicationMapper() {
        return Mappers.getMapper(ApplicationMapper.class);
    }
}