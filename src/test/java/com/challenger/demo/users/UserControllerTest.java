package com.challenger.demo.users;

import com.challenger.demo.TestMySQLContainer;
import com.challenger.demo.challenges.ChallengeRepository;
import com.challenger.demo.challenges.ChallengeService;
import com.challenger.demo.util.CommonRequestsService;
import com.challenger.demo.util.UserUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
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

    @Autowired
    CommonRequestsService commonRequestsService;

    @Test
    @Sql(scripts = {"/import_user.sql", "/import_challenges_to_user.sql"})
    public void testAddingChallenge() throws Exception {
        User dummyUser = UserUtil.createDummyUser("test@abv.bg", "test");

        String responseBody = commonRequestsService.executeAuthentication(dummyUser)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String contentAsString = this.mockMvc.perform(get("/api/v1/users/")
                        .headers(commonRequestsService.addAuthorizationHeader(responseBody)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User user = UserUtil.convertFromStringToJson(contentAsString);
        Assertions.assertNotNull(user);
    }
}


