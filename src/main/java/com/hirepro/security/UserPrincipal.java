package com.hirepro.security;

import com.hirepro.model.User;
import com.hirepro.model.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserPrincipal implements UserDetails {
    private final Long id;
    private final String email;
    private final String name;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;
    private final UserStatus status;

    public UserPrincipal(Long id, String name, String email, String password,
                         Collection<? extends GrantedAuthority> authorities,
                         UserStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.status = status;
    }

    public static UserPrincipal create(User user) {
        // Convert your Role enum to GrantedAuthority
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority), // Single role per user
                user.getStatus()
        );
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != UserStatus.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatus.ACTIVE;
    }
}