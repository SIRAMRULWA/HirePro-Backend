// src/main/java/com/hirepro/repository/AuditLogRepository.java
package com.hirepro.repository;

import com.hirepro.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}