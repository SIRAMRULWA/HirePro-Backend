// src/main/java/com/hirepro/model/enums/UserStatus.java
package com.hirepro.model.enums;

public enum UserStatus {
    ACTIVE,         // User can access the system
    INACTIVE,       // User cannot access the system
    SUSPENDED,      // Temporary suspension
    PENDING,
    DEACTIVATED,// Waiting for approval
    DELETED,         // Soft-deleted user
    LOCKED
}