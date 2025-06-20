package com.hirepro.controller.user;

import com.hirepro.dto.request.application.CreateApplicationRequest;
import com.hirepro.dto.request.application.UpdateApplicationRequest;
import com.hirepro.dto.response.application.ApplicationResponse;
import com.hirepro.dto.response.ApiResponse;
import com.hirepro.service.ApplicationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/applications")
@PreAuthorize("hasRole('USER')")
public class UserApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(@Valid @RequestBody CreateApplicationRequest request,
                                                                 Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(applicationService.createApplication(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getUserApplications(Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(applicationService.getApplicationsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getApplicationById(@PathVariable String id,
                                                                  Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(applicationService.getApplicationByIdForUser(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> updateApplication(@PathVariable String id,
                                                                 @Valid @RequestBody UpdateApplicationRequest request,
                                                                 Authentication authentication) {
        String userId = authentication.getName();
        return ResponseEntity.ok(applicationService.updateApplicationForUser(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteApplication(@PathVariable String id,
                                                         Authentication authentication) {
        String userId = authentication.getName();
        applicationService.deleteApplicationForUser(id, userId);
        return ResponseEntity.ok(new ApiResponse(true, "Application deleted successfully."));
    }
}
