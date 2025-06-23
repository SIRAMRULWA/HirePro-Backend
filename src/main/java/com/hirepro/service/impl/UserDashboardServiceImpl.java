//package com.hirepro.service.impl;
//
//import com.hirepro.dto.response.user.*;
//import com.hirepro.model.*;
//import com.hirepro.model.enums.*;
//import com.hirepro.repository.*;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class UserDashboardServiceImpl implements com.hirepro.service.UserDashboardService {
//
//    private final ApplicationRepository applicationRepository;
//    private final JobRepository jobRepository;
//    private final UserRepository userRepository;
//    private final ActivityLogRepository activityLogRepository;
//
//    @Override
//    public UserDashboardStatsResponse getDashboardStats(String userId) {
//        return UserDashboardStatsResponse.builder()
//                .totalApplications(applicationRepository.countByUserId(userId))
//                .pendingApplications(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.PENDING))
//                .interviewScheduled(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.INTERVIEW_SCHEDULED))
//                .rejectedApplications(applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.REJECTED))
//                .profileCompletion(calculateProfileCompletion(userId))
//                .welcomeMessage(generateWelcomeMessage(userId))
//                .build();
//    }
//
//    @Override
//    public List<RecentApplicationResponse> getRecentApplications(String userId, int limit) {
//        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "appliedAt"));
//        return applicationRepository.findByUserId(userId, pageable).stream()
//                .map(this::convertToRecentApplicationResponse)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<UserRecommendedJobResponse> getRecommendedJobs(String userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "postedDate"));
//        return jobRepository.findByStatus(JobStatus.OPEN, pageable).stream()
//                .filter(job -> matchesUserSkills(job, user))
//                .map(this::convertToRecommendedJobResponse)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public int getProfileCompletion(String userId) {
//        return calculateProfileCompletion(userId);
//    }
//
//    @Override
//    public List<UserActivityResponse> getRecentActivities(String userId, int limit) {
//        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
//        return activityLogRepository.findByUserId(userId, pageable).stream()
//                .map(this::convertToActivityResponse)
//                .collect(Collectors.toList());
//    }
//
//    // Helper methods
//    private int calculateProfileCompletion(String userId) {
//        User user = userRepository.findById(userId).orElseThrow();
//        int totalFields = 6;
//        int completedFields = 0;
//
//        if (user.getName() != null && !user.getName().isEmpty()) completedFields++;
//        if (user.getEmail() != null && !user.getEmail().isEmpty()) completedFields++;
//        if (user.getProfilePictureUrl() != null && !user.getProfilePictureUrl().isEmpty()) completedFields++;
//        if (user.getJobTitle() != null && !user.getJobTitle().isEmpty()) completedFields++;
//        if (user.getLocation() != null && !user.getLocation().isEmpty()) completedFields++;
//        if (user.getBio() != null && !user.getBio().isEmpty()) completedFields++;
//
//        return (int) ((completedFields / (double) totalFields) * 100);
//    }
//
//    private String generateWelcomeMessage(String userId) {
//        User user = userRepository.findById(userId).orElseThrow();
//        int hour = LocalDateTime.now().getHour();
//        String greeting = (hour < 12) ? "Good morning" :
//                (hour < 18) ? "Good afternoon" : "Good evening";
//        return String.format("%s, %s!", greeting, user.getName());
//    }
//
//    private boolean matchesUserSkills(Job job, User user) {
//        if (user.getSkills() == null || user.getSkills().isEmpty()) return true;
//        return job.getRequirements().toLowerCase().contains(user.getSkills().toLowerCase());
//    }
//
//    private RecentApplicationResponse convertToRecentApplicationResponse(Application application) {
//        return RecentApplicationResponse.builder()
//                .id(application.getId())
//                .jobTitle(application.getJob().getTitle())
//                .companyName(application.getJob().getCompanyName())
//                .appliedDate(application.getAppliedAt())
//                .status(application.getStatus().name())
//                .build();
//    }
//
//    private RecommendedJobResponse convertToRecommendedJobResponse(Job job) {
//        return UserRecommendedJobResponse.builder()
//                .id(job.getId())
//                .title(job.getTitle())
//                .company(job.getCompanyName())
//                .requiredSkills(List.of(job.getRequirements().split(",")))
//                .salaryRange(job.getSalaryRange())
//                .location(job.getLocation())
//                .postedDate(job.getPostedDate())
//                .build();
//    }
//
//    private UserActivityResponse convertToActivityResponse(ActivityLog activity) {
//        return UserActivityResponse.builder()
//                .activityType(activity.getActivityType().name())
//                .description(activity.getDescription())
//                .timestamp(activity.getCreatedAt())
//                .relatedEntityId(activity.getRelatedEntityId())
//                .build();
//    }
//}