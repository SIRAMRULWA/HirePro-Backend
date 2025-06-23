// src/main/java/com/hirepro/model/User.java
package com.hirepro.model;

import com.hirepro.model.enums.Role;
import com.hirepro.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String phone;  // Added phone field

    private String profilePictureUrl;
    private String jobTitle;
    private String location;
    private String bio;

    @ElementCollection
    private List<String> skills = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    private boolean emailVerified = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Helper method for profile completion calculation
    public int calculateProfileCompletion() {
        int totalFields = 8; // Updated to include phone (name, email, phone, profilePicture, jobTitle, location, bio, skills)
        int completedFields = 0;

        if (this.name != null && !this.name.isEmpty()) completedFields++;
        if (this.email != null && !this.email.isEmpty()) completedFields++;
        if (this.phone != null && !this.phone.isEmpty()) completedFields++;  // Added phone check
        if (this.profilePictureUrl != null && !this.profilePictureUrl.isEmpty()) completedFields++;
        if (this.jobTitle != null && !this.jobTitle.isEmpty()) completedFields++;
        if (this.location != null && !this.location.isEmpty()) completedFields++;
        if (this.bio != null && !this.bio.isEmpty()) completedFields++;
        if (!this.skills.isEmpty()) completedFields++;

        return (int) ((completedFields / (double) totalFields) * 100);
    }

    // Convenience method to update profile fields
    public void updateProfileDetails(String name, String email, String phone) {
        if (name != null) {
            this.name = name;
        }
        if (email != null) {
            this.email = email;
        }
        if (phone != null) {
            this.phone = phone;
        }
    }
}