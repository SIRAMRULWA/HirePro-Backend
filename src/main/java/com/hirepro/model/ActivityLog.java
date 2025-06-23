// src/main/java/com/hirepro/model/ActivityLog.java
package com.hirepro.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class ActivityLog {
    public enum ActivityType {
        PROFILE_UPDATE,
        APPLICATION_SUBMITTED,
        INTERVIEW_SCHEDULED,
        PASSWORD_CHANGED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    private String description;
    private String relatedEntityId;
    private Long userId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
