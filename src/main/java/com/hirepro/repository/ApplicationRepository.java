// src/main/java/com/hirepro/repository/ApplicationRepository.java
package com.hirepro.repository;

import com.hirepro.model.Application;
import com.hirepro.model.enums.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.QueryHint;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

    // Basic query methods
    List<Application> findByJobId(String jobId);
    List<Application> findByUserId(String userId);
    List<Application> findByStatus(ApplicationStatus status);

    // Paginated versions
    Page<Application> findByJobId(String jobId, Pageable pageable);
    Page<Application> findByUserId(String userId, Pageable pageable);
    Page<Application> findByStatus(ApplicationStatus status, Pageable pageable);

    // Custom queries with status filtering
    @Query("SELECT a FROM Application a WHERE a.userId = :userId AND a.status = :status")
    List<Application> findByUserIdAndStatus(
            @Param("userId") String userId,
            @Param("status") ApplicationStatus status);

    @Query("SELECT a FROM Application a WHERE a.jobId = :jobId AND a.status = :status")
    List<Application> findByJobIdAndStatus(
            @Param("jobId") String jobId,
            @Param("status") ApplicationStatus status);

    // Paginated version with status filter
    @Query("SELECT a FROM Application a WHERE a.userId = :userId AND a.status = :status")
    Page<Application> findByUserIdAndStatus(
            @Param("userId") String userId,
            @Param("status") ApplicationStatus status,
            Pageable pageable);

    // Optimized queries with hints
    @QueryHints(@QueryHint(name = "org.hibernate.cacheable", value = "true"))
    List<Application> findByJobIdOrderByAppliedAtDesc(String jobId);

    // Count queries
    long countByJobId(String jobId);
    long countByUserId(String userId);
    long countByStatus(ApplicationStatus status);

    // Optimized count queries with status
    @Query("SELECT COUNT(a) FROM Application a WHERE a.userId = :userId AND a.status = :status")
    long countByUserIdAndStatus(
            @Param("userId") String userId,
            @Param("status") ApplicationStatus status);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.jobId = :jobId AND a.status = :status")
    long countByJobIdAndStatus(
            @Param("jobId") String jobId,
            @Param("status") ApplicationStatus status);

    // Exists queries
    boolean existsByJobIdAndUserId(String jobId, String userId);

    // Additional exists query with status
    boolean existsByJobIdAndUserIdAndStatus(String jobId, String userId, ApplicationStatus status);
}