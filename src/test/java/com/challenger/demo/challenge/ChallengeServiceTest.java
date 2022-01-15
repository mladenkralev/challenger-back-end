package com.challenger.demo.challenge;

import com.challenger.demo.TestMySQLContainer;
import com.challenger.demo.challenges.Challenge;
import com.challenger.demo.challenges.ChallengeRepository;
import com.challenger.demo.challenges.ChallengeService;
import com.challenger.demo.util.ChallengeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("integrationTest,default")
@Profile("integrationTest")
@Transactional
@SpringBootTest(classes = TestMySQLContainer.class)
public class ChallengeServiceTest {
    @Autowired
    ChallengeService challengeService;

    @Autowired
    ChallengeRepository repository;

    @Test
    public void addingNullChallenge() {
        Assertions.assertThrows(NullPointerException.class, () -> challengeService.addingChallenge(null));
    }

    @Test
    public void testAddingChallenge() {
        Challenge challenge = ChallengeUtil.createDummyChallenge(ChallengeUtil.dummyTitle);
        challengeService.addingChallenge(challenge);
        Assertions.assertTrue(repository.findById(challenge.id).isPresent());
    }


    @Test
    void testDeletingChallenge() {
        Challenge challenge = ChallengeUtil.createDummyChallenge(ChallengeUtil.dummyTitle);
        challengeService.addingChallenge(challenge);
        Assertions.assertTrue(repository.findById(challenge.id).isPresent(), String.format("Challenge %s was not created...", challenge));

        repository.delete(challenge);
        Assertions.assertFalse(repository.findById(challenge.id).isPresent(), String.format("Challenge %s did not delete...", challenge));
    }

    @Test
    void testUpdatingChallenge() {
        Challenge challenge = ChallengeUtil.createDummyChallenge(ChallengeUtil.dummyTitle);

        challengeService.addingChallenge(challenge);
        Assertions.assertTrue(repository.findById(challenge.id).isPresent(), String.format("Challenge %s was not created...", challenge));

        String updatedTitle= "Updated title longer then 12 symbols";
        Challenge updatedChallenge = ChallengeUtil.createDummyChallenge(updatedTitle);
        challengeService.updateChallenge(challenge, updatedChallenge);

        updatedChallenge = repository.getOne(challenge.id);
        Assertions.assertEquals(updatedTitle, repository.getOne(challenge.id).title, String.format("Challenge %s was not updated to %s...", challenge, updatedChallenge));
    }
}
