// src/main/java/com/hirepro/model/enums/Role.java
package com.hirepro.model.enums;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    RECRUITER("ROLE_RECRUITER"),
    CANDIDATE("ROLE_CANDIDATE"),
    INTERVIEWER("ROLE_INTERVIEWER"),
    USER("ROLE_USER");  // <-- Added USER role

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    // name() method is inherited from Enum and returns the enum constant name
}
