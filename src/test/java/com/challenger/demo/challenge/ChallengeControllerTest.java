package com.challenger.demo.challenge;

import com.challenger.demo.TestMySQLContainer;
import com.challenger.demo.challenges.ChallengeController;
import com.challenger.demo.challenges.ChallengeService;
import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import com.challenger.demo.challenges.models.ChallengeRequest;
import com.challenger.demo.challenges.models.ChallengeResponse;
import com.challenger.demo.util.ChallengeUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.Errors;
import org.testcontainers.shaded.okhttp3.Challenge;

import javax.transaction.Transactional;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ActiveProfiles(profiles = {"integrationTest", "default"})
@Profile("integrationTest")
@ContextConfiguration(classes = TestMySQLContainer.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ChallengeControllerTest {

    @Autowired
    ChallengeService challengeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChallengeUtil challengeUtil;

    @MockBean
    Errors errors;

    @Test
    public void testCreateChallenge() throws Exception {
        ChallengeRequest challenge = challengeUtil.createDummyChallenge(ChallengeUtil.dummyTitle);

        String jsonString = challengeUtil.transformChallengeRequestToJsonString(challenge);
        mockMvc.perform(post("/api/v1/challenges")
                .content(jsonString)
                .contentType("application/json"));

        List<ChallengeDatabaseModel> allChallenges = challengeService.getChallenges();
        Assertions.assertThat(allChallenges.size()).isEqualTo(1);
        Assertions.assertThat(allChallenges.get(0).title).isEqualTo(ChallengeUtil.dummyTitle);
    }

    @Test
    public void testDeletingChallenge() throws Exception {
        ChallengeRequest challenge = challengeUtil.createDummyChallenge(ChallengeUtil.dummyTitle);
        String jsonString = challengeUtil.transformChallengeRequestToJsonString(challenge);
        mockMvc.perform(post("/api/v1/challenges")
                .content(jsonString)
                .contentType("application/json"));

        List<ChallengeDatabaseModel> allChallenges = challengeService.getChallenges();
        Assertions.assertThat(allChallenges.size()).isEqualTo(1);

        ChallengeDatabaseModel challengeDatabaseModel = allChallenges.get(0);
        Assertions.assertThat(allChallenges.get(0).title).isEqualTo(ChallengeUtil.dummyTitle);

        mockMvc.perform(delete("/api/v1/challenges/{id}", challengeDatabaseModel.id)
                .contentType("application/json"));

        allChallenges = challengeService.getChallenges();
        Assertions.assertThat(allChallenges.size()).isEqualTo(0);

    }

    @Test
    @Sql(scripts = {"/import_user.sql", "/import_challenges_to_user.sql"})
    public void testCompletingChallenge() throws Exception {

        List<ChallengeDatabaseModel> allChallenges = challengeService.getChallenges();
        Assertions.assertThat(allChallenges.size()).isEqualTo(2);

        ChallengeDatabaseModel challengeDatabaseModel = allChallenges.get(0);
        long daysBetween = DAYS.between(challengeDatabaseModel.startDate, challengeDatabaseModel.endDate);

        for(int index=0; index < daysBetween; index++) {
            mockMvc.perform(post("/api/v1/challenges/{id}/progress", challengeDatabaseModel.id)
                    .contentType("application/json"));
        }

        ChallengeDatabaseModel completedChallenge = challengeService.getChallenges().get(0);
        Assertions.assertThat(completedChallenge.numberOfProgressHits).isEqualTo(100);

    }

}