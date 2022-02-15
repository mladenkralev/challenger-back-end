package com.challenger.demo.challenges;

import com.challenger.demo.annotation.TrackExecutionTime;
import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import com.challenger.demo.challenges.models.ChallengeRequest;
import com.challenger.demo.challenges.models.ChallengeResponse;
import com.challenger.demo.challenges.transformer.ChallengeTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private ChallengeTransformer challengeTransformer;

    @GetMapping("/challenges")
    @TrackExecutionTime
    public List<ChallengeResponse> getChallenges() {
        return challengeService.getChallenges().stream()
                .map(databaseEntry -> challengeTransformer.toResponse(databaseEntry))
                .collect(Collectors.toList());
    }

    @GetMapping("/challenges/{id}")
    @TrackExecutionTime
    public ChallengeResponse getChallenge(@PathVariable Long id) {
        return challengeTransformer.toResponse(challengeService.getChallenge(id));
    }

    @PostMapping("/challenges")
    @TrackExecutionTime
    public ChallengeResponse createChallenge(@Valid @RequestBody ChallengeRequest challenge) {
        ChallengeDatabaseModel challengeDatabaseModel = challengeTransformer.toDatabaseEntity(challenge);
        return challengeTransformer.toResponse(challengeService.addingChallenge(challengeDatabaseModel));
    }

    @DeleteMapping ("/challenges/{id}")
    @TrackExecutionTime
    public ChallengeResponse deleteChallenge(@PathVariable Long id) {
        return challengeTransformer.toResponse(challengeService.deleteChallenge(id));
    }
}
