package com.challenger.demo.security;

import com.challenger.demo.TestMySQLContainer;

import com.challenger.demo.challenges.ChallengeService;
import com.challenger.demo.security.controller.AuthenticationEndpoint;
import com.challenger.demo.security.controller.AuthenticationRequest;
import com.challenger.demo.users.User;
import com.challenger.demo.users.UserRepository;
import com.challenger.demo.util.UserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;


@ActiveProfiles(profiles = { "prd" , "integrationTest"} )
@Profile("integrationTest")
@AutoConfigureMockMvc
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
        ResponseEntity<?> authenticate = authenticationEndpoint.authenticate(requestBody);
        Assertions.assertEquals(authenticate.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @Transactional
    public void testInvalidLogin() {
        AuthenticationRequest requestBody = new AuthenticationRequest("test@abv.bg", "test");
        Assertions.assertThrows(BadCredentialsException.class, () -> authenticationEndpoint.authenticate(requestBody));
    }
}
