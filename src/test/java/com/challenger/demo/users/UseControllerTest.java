package com.challenger.demo.users;

import com.challenger.demo.TestMySQLContainer;
import com.challenger.demo.challenges.ChallengeRepository;
import com.challenger.demo.challenges.ChallengeService;
import com.challenger.demo.users.models.UserRequest;
import com.challenger.demo.users.models.UserResponse;
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

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles(profiles = {"integrationTest", "prd", "default"})
@Profile("integrationTest")
@AutoConfigureMockMvc
@SpringBootTest(classes = TestMySQLContainer.class)
@Transactional
public class UseControllerTest {

    @Autowired
    ChallengeService challengeService;

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
    public void testLoginUser() throws Exception {
        UserRequest dummyUser = UserUtil.createDummyUser("test@abv.bg", "test");

        commonRequestsService.executeAuthentication(dummyUser)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

    }

    @Test
    @Sql(scripts = {"/import_user.sql", "/import_challenges_to_user.sql"})
    public void testInvalidLoginUser() throws Exception {
        UserRequest dummyUser = UserUtil.createDummyUser("test@abv.bg", "invalidPassword");

        commonRequestsService.executeAuthentication(dummyUser)
                .andExpect(status().isUnauthorized())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @Sql(scripts = {"/import_user.sql", "/import_challenges_to_user.sql"})
    public void testGetAllUsers() throws Exception {
        UserRequest dummyUser = UserUtil.createDummyUser("test@abv.bg", "test");

        String responseBody = commonRequestsService.executeAuthentication(dummyUser)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String contentAsString = this.mockMvc.perform(get("/api/v1/users/")
                        .headers(commonRequestsService.addAuthorizationHeader(responseBody)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserResponse user = UserUtil.convertFromStringToJson(contentAsString);
        Assertions.assertNotNull(user);
    }

    @Test
    public void     testCreateUsers() throws Exception {
        String password = "test";
        UserRequest dummyUser = UserUtil.createDummyUser("test@abv.bg", password);

        String jsonUser = objectMapper.writeValueAsString(dummyUser);

        String response = this.mockMvc.perform(post("/api/v1/users/")
                        .contentType("application/json")
                        .content(jsonUser))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserResponse user = UserUtil.convertFromStringToJson(response);
        Assertions.assertNotNull(user);

        // validate that the user exists
        String responseBody = commonRequestsService.executeAuthentication(dummyUser)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String contentAsString = this.mockMvc.perform(get("/api/v1/users/")
                        .headers(commonRequestsService.addAuthorizationHeader(responseBody)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        user = UserUtil.convertFromStringToJson(contentAsString);
        Assertions.assertNotNull(user);
    }

}


