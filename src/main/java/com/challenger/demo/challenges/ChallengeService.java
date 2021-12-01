package com.challenger.demo.challenges;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ChallengeService {
    @Autowired
    private ChallengeRepository challengeRepository;

    public Challenge addingChallenge(@Nullable Challenge challenge) {
        Objects.requireNonNull(challenge, String.format("Rejected incoming challenge object. Value: %s ",challenge));
        return challengeRepository.saveAndFlush(challenge);
    }

    public Challenge deleteChallenge(Long id) {
        Challenge deletedChallenge = challengeRepository.getOne(id);
        challengeRepository.deleteById(id);
        return deletedChallenge;
    }

    public Challenge updateChallenge(Challenge existingChallenge, Challenge newChallenge) {
        BeanUtils.copyProperties(newChallenge, existingChallenge,
                "id", "createdByUserChallenges", "assignedToUserChallenges");
        challengeRepository.save(existingChallenge);
        return existingChallenge;
    }

    public List<Challenge> getChallenges() {
        return challengeRepository.findAll();
    }

    public Challenge getChallenge(Long id) {
        return challengeRepository.findById(id).orElse(new Challenge());
    }

    public Challenge readChallenge(Long id) {
        return challengeRepository.findById(id).orElse(new Challenge());
    }

}
