package com.hirepro.repository;

import com.hirepro.model.User;
import com.hirepro.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    Page<User> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);
    long countByStatus(UserStatus status);
    long countByCreatedAtAfter(LocalDateTime date);
}