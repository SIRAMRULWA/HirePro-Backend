// src/main/java/com/hirepro/service/impl/AuditLogServiceImpl.java
package com.hirepro.service.impl;

import com.hirepro.model.AuditLog;
import com.hirepro.repository.AuditLogRepository;
import com.hirepro.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public void logAction(String action, String entityType, String entityId, String performedBy, String details) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setPerformedBy(performedBy);
        log.setDetails(details);
        auditLogRepository.save(log);
    }
}