package com.challenger.demo.security.controller;

import com.challenger.demo.security.configuration.CustomUserDetails;
import com.challenger.demo.security.configuration.CustomUserDetailsService;
import com.challenger.demo.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@Profile(value = "prd")
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials for email " + request.getEmail());
        }

        final CustomUserDetails userDetails = userDetailsService
                .loadUserByUsername(request.getEmail());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return (new AuthenticationResponse(jwt));
    }

}
