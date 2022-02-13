package com.challenger.demo.users.models;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class UserRequest {
    public String email;
    public String username;
    public String password;
}
