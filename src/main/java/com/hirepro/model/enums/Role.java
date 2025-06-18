// src/main/java/com/hirepro/model/enums/Role.java
package com.hirepro.model.enums;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    RECRUITER("ROLE_RECRUITER"),
    CANDIDATE("ROLE_CANDIDATE"),
    INTERVIEWER("ROLE_INTERVIEWER");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    // The name() method is automatically provided by all enums in Java
    // This is what's being called when you do user.getRole().name()
}