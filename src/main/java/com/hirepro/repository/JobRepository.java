// src/main/java/com/hirepro/repository/JobRepository.java
package com.hirepro.repository;

import com.hirepro.model.Job;
import com.hirepro.model.enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, String> {
    // Existing methods
    Page<Job> findByStatus(JobStatus status, Pageable pageable);
    Page<Job> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Job> findByLocationContainingIgnoreCase(String location, Pageable pageable);
    Page<Job> findByTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String title, String location, Pageable pageable);

    // New methods for dashboard statistics
    long countByStatus(JobStatus status);

    @Query("SELECT COUNT(j) FROM Job j WHERE j.status = 'ACTIVE'")
    long countActiveJobs();

    // For filtering active jobs
    Page<Job> findByStatusAndTitleContainingIgnoreCase(
            JobStatus status, String title, Pageable pageable);

    Page<Job> findByStatusAndLocationContainingIgnoreCase(
            JobStatus status, String location, Pageable pageable);

    Page<Job> findByStatusAndTitleContainingIgnoreCaseOrLocationContainingIgnoreCase(
            JobStatus status, String title, String location, Pageable pageable);

    // For admin dashboard (if needed)
    @Query("SELECT COUNT(j) FROM Job j WHERE j.status IN :statuses")
    long countByStatuses(@Param("statuses") List<JobStatus> statuses);
}