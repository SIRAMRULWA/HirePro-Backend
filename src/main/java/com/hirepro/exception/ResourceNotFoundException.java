// src/main/java/com/hirepro/exception/ResourceNotFoundException.java
package com.hirepro.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s not found with %s: %s", resource, field, value));
    }
}