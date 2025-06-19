package com.hirepro.repository;

import com.hirepro.model.User;
import com.hirepro.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Existing query methods
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);
    long countByStatus(UserStatus status);
    long countByCreatedAtAfter(LocalDateTime date);

    // Email-related query methods
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);  // New method for email existence check

    // Additional useful methods you might need
    boolean existsByEmailAndStatus(String email, UserStatus status);
    Optional<User> findByEmailAndStatus(String email, UserStatus status);
}