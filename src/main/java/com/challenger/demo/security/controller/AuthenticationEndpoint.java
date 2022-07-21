package com.challenger.demo.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Profile("prd")
@RestController
@RequestMapping("/api/v1")
public class AuthenticationEndpoint {

    @Autowired
    public AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        return ResponseEntity.ok(authenticationResponse);
    }
}
