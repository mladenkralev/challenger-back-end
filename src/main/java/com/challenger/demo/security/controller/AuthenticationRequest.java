package com.challenger.demo.security.controller;


import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @Getter
    private String email;
    @Getter
    private String password;
}
