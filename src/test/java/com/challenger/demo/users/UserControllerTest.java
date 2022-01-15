package com.challenger.demo.users;

import com.challenger.demo.TestMySQLContainer;
import com.challenger.demo.challenges.ChallengeRepository;
import com.challenger.demo.challenges.ChallengeService;
import com.challenger.demo.security.controller.AuthenticationRequest;
import com.challenger.demo.util.AuthenticationRequestUtil;
import com.challenger.demo.util.JwtHelper;
import com.challenger.demo.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(profiles = {"integrationTest", "prd", "default"})
@Profile("integrationTest")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestMySQLContainer.class)
public class UserControllerTest {

    @Autowired
    ChallengeService challengeService;

    @Autowired
    ChallengeRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    Errors errors;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Sql(scripts = {"/import_employees.sql"})
    public void testAddingChallenge() throws Exception {
        User dummyUser = UserUtil.createDummyUser("test@abv.bg", "test");

        AuthenticationRequest authenticationRequest = AuthenticationRequestUtil.createDummyAuthenticationRequest(dummyUser);
        String jsonAuthUserBody = AuthenticationRequestUtil.transformRequestToJsonString(authenticationRequest);

        String responseBody = this.mockMvc.perform(post("/api/v1/authenticate")
                        .content(jsonAuthUserBody)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " +  JwtHelper.extractJsonTokenFromString(responseBody));
        String contentAsString = this.mockMvc.perform(get("/api/v1/users/")
                        .headers(authHeader))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User user = UserUtil.convertFromStringToJson(contentAsString);
        Assertions.assertNotNull(user);
    }
}


