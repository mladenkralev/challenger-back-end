package com.challenger.demo.security.configuration;

import org.springframework.security.core.userdetails.UserDetails;

public interface ExtendedUserDetails extends UserDetails {
    String getEmail();
}
