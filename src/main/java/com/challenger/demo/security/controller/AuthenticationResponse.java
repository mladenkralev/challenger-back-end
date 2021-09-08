package com.challenger.demo.security.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    @Getter
    @Setter
    private String jwt;
}
