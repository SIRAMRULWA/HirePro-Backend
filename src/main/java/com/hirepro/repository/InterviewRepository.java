package com.hirepro.repository;

import com.hirepro.model.Interview;
import com.hirepro.model.enums.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
    long countByApplicationUserIdAndStatus(Long userId, InterviewStatus status);
}