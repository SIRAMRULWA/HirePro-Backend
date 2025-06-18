// src/main/java/com/hirepro/repository/JobRepository.java
package com.hirepro.repository;

import com.hirepro.model.Job;
import com.hirepro.model.enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {
    Page<Job> findByStatus(JobStatus status, Pageable pageable);
    Page<Job> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Job> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    Page<Job> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String title, String location, Pageable pageable);
}