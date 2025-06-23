package com.hirepro.service;

import com.hirepro.dto.response.admin.AdminStatsResponse;
import com.hirepro.dto.response.ApiResponse;

public interface AdminService {
    ApiResponse getSystemStats();
}
