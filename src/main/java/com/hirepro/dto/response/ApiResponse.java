// src/main/java/com/hirepro/dto/response/ApiResponse.java
package com.hirepro.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
}