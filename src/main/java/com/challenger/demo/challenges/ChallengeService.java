package com.challenger.demo.challenges;

import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ChallengeService {
    @Autowired
    private ChallengeRepository challengeRepository;

    public ChallengeDatabaseModel addingChallenge(@Nullable ChallengeDatabaseModel challenge) {
        Objects.requireNonNull(challenge, String.format("Rejected incoming challenge object. Value: %s ",challenge));
        return challengeRepository.saveAndFlush(challenge);
    }

    public ChallengeDatabaseModel deleteChallenge(Long id) {
        ChallengeDatabaseModel deletedChallenge = challengeRepository.getOne(id);
        challengeRepository.deleteById(id);
        return deletedChallenge;
    }

    public ChallengeDatabaseModel updateChallenge(ChallengeDatabaseModel existingChallenge, ChallengeDatabaseModel newChallenge) {
        BeanUtils.copyProperties(newChallenge, existingChallenge,
                "id", "createdByUserChallenges", "assignedToUserChallenges");
        challengeRepository.save(existingChallenge);
        return existingChallenge;
    }

    public List<ChallengeDatabaseModel> getChallenges() {
        return challengeRepository.findAll();
    }

    public ChallengeDatabaseModel getChallenge(Long id) {
        return challengeRepository.findById(id).orElse(new ChallengeDatabaseModel());
    }

    public ChallengeDatabaseModel readChallenge(Long id) {
        return challengeRepository.findById(id).orElse(new ChallengeDatabaseModel());
    }

}
