package com.hrm.project_spring.security;

import com.hrm.project_spring.entity.Role;
import com.hrm.project_spring.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
public class CustomUserPrincipal implements UserDetails {

    private final User user;

    public CustomUserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return authorities;
        }

        for (Role role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));

            if (role.getPermissions() != null) {
                role.getPermissions().forEach(permission ->
                    authorities.add(new SimpleGrantedAuthority(permission.getCode()))
                );
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "ACTIVE".equalsIgnoreCase(user.getStatus()) || user.getStatus() == null;
    }
}