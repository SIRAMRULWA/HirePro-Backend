// src/main/java/com/hirepro/model/Job.java
package com.hirepro.model;

import com.hirepro.model.enums.JobStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "jobs")
@Getter
@Setter
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String requirements;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String salaryRange;

    @Enumerated(EnumType.STRING)
    private JobStatus status;

    @CreationTimestamp
    private LocalDateTime postedDate;

    @Column(nullable = false)
    private LocalDateTime closingDate;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}