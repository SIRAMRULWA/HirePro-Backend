// src/main/java/com/hirepro/repository/ActivityLogRepository.java
package com.hirepro.repository;

import com.hirepro.model.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    Page<ActivityLog> findByUserId(Long userId, Pageable pageable);
}