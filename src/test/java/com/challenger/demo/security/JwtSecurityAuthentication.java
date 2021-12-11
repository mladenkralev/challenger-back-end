package com.challenger.demo.security;

import com.challenger.demo.TestMySQLContainer;

import com.challenger.demo.challenges.ChallengeService;
import com.challenger.demo.security.controller.AuthenticationEndpoint;
import com.challenger.demo.security.controller.AuthenticationRequest;
import com.challenger.demo.users.User;
import com.challenger.demo.users.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;


@ActiveProfiles("prd")
@Profile("integrationTest")
@SpringBootTest(classes = TestMySQLContainer.class)
public class JwtSecurityAuthentication {
    @Autowired
    AuthenticationEndpoint authenticationEndpoint;

    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    public void testDummyLogin() throws Exception {
        String email = "test@abv.bg";
        String password = "test";
        String encodedPassword = passwordEncoder.encode(password);

        User user = UserUtil.createDummyUser(email, encodedPassword);
        userRepository.saveAndFlush(user);

        AuthenticationRequest requestBody = new AuthenticationRequest(email, password);
        Assertions.assertThrows(BadCredentialsException.class, () -> authenticationEndpoint.authenticate(requestBody));
    }

    @Test
    @Transactional
    public void testInvalidLogin() throws Exception {
        String encodedPassword = passwordEncoder.encode("test");
        User user = User.builder()
                .id(0)
                .username("test")
                .password(encodedPassword)
                .email("test@abv.bg")
                .roles("ADMIN")
                .active(true)
                .build();
        userRepository.saveAndFlush(user);
        AuthenticationRequest requestBody = new AuthenticationRequest("test@abv.bg", "test");
        Assertions.assertThrows(BadCredentialsException.class, () -> authenticationEndpoint.authenticate(requestBody));
    }
}
