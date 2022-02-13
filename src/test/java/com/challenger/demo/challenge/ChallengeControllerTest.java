package com.challenger.demo.challenge;

import com.challenger.demo.TestMySQLContainer;
import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import com.challenger.demo.challenges.ChallengeController;
import com.challenger.demo.challenges.ChallengeService;
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
import org.springframework.validation.Errors;

import javax.transaction.Transactional;
import java.util.List;


@ActiveProfiles(profiles = {"integrationTest", "default"})
@Profile("integrationTest")
@ContextConfiguration(classes = TestMySQLContainer.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ChallengeControllerTest {

    @Autowired
    private ChallengeController controller;

    @Autowired
    private ChallengeService service;

    @Autowired
    private ChallengeUtil challengeUtil;

    @MockBean
    Errors errors;

    @Test
    public void testCreateChallenge() throws Exception {
        ChallengeDatabaseModel challenge = challengeUtil.createDummyChallenge(ChallengeUtil.dummyTitle);

        controller.createChallenge(challenge, errors);

        List<ChallengeDatabaseModel> allChallenges = service.getChallenges();
        Assertions.assertThat(allChallenges.size()).isEqualTo(1);
        Assertions.assertThat(allChallenges.get(0).title).isEqualTo(ChallengeUtil.dummyTitle);
    }

    @Test
    public void testDeletingChallenge() throws Exception {
        ChallengeDatabaseModel challenge = challengeUtil.createDummyChallenge(ChallengeUtil.dummyTitle);
        service.addingChallenge(challenge);

        List<ChallengeDatabaseModel> allChallenges = service.getChallenges();
        Assertions.assertThat(allChallenges.size()).isEqualTo(1);
        Assertions.assertThat(allChallenges.get(0).title).isEqualTo(ChallengeUtil.dummyTitle);

        controller.deleteChallenge(allChallenges.get(0).id);

        allChallenges = service.getChallenges();
        Assertions.assertThat(allChallenges.size()).isEqualTo(0);

    }

}
