package com.challenger.demo.security.configuration;

import com.challenger.demo.users.models.UserDatabaseModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements ExtendedUserDetails {

    private final String email;
    private final String username;
    private final String password;
    private final boolean active;
    private final List<GrantedAuthority> grantedAuthorityList;

    public CustomUserDetails(UserDatabaseModel user) {
        this.email = user.email;
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.active = user.isActive();
        this.grantedAuthorityList = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
        return this.active;
    }

    @Override
    public String getEmail() {
        return this.email;
    }
}
