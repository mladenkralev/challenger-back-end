package com.challenger.demo.challenges.transformer;

import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import com.challenger.demo.challenges.models.ChallengeRequest;
import com.challenger.demo.challenges.models.ChallengeResponse;
import org.springframework.stereotype.Service;

@Service
public class ChallengeTransformer {

    public ChallengeDatabaseModel toDatabaseEntity(ChallengeRequest challengeRequest) {
        return ChallengeDatabaseModel.builder()
                .id(challengeRequest.id)
                .description(challengeRequest.description)
                .occurrences(challengeRequest.occurrences)
                .title(challengeRequest.title)
                .startDate(challengeRequest.startDate)
                .endDate(challengeRequest.endDate)
                .build();

    }

    public ChallengeResponse toResponse(ChallengeDatabaseModel challengeDatabaseModel) {
        return ChallengeResponse.builder()
                .id(challengeDatabaseModel.id)
                .description(challengeDatabaseModel.description)
                .occurrences(challengeDatabaseModel.occurrences)
                .title(challengeDatabaseModel.title)
                .startDate(challengeDatabaseModel.startDate)
                .endDate(challengeDatabaseModel.endDate)
                .build();

    }
}
