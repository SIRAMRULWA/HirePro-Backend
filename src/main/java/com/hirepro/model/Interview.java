package com.hirepro.model;

import com.hirepro.model.enums.InterviewStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
@Getter
@Setter
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "application_user_id") // This should match your countBy query
    private User applicationUser; // Must match the property name in your repository method

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String feedback;

    // Other fields as needed
}