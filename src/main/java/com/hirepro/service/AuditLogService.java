// src/main/java/com/hirepro/service/AuditLogService.java
package com.hirepro.service;

public interface AuditLogService {
    void logAction(String action, String entityType, String entityId, String performedBy, String details);
}