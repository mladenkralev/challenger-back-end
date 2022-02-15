package com.challenger.demo.util;

import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import com.challenger.demo.challenges.models.ChallengeRequest;
import com.challenger.demo.models.embeded.Occurrences;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChallengeUtil {
    @Autowired
    private ObjectMapper objectMapper;

    public static String dummyTitle = "This is dummy title and it longer thant 12 symbols";

    public ChallengeRequest createDummyChallenge(String title) {
        return ChallengeRequest.builder()
                .title(title)
                .description("This is dummy title and it longer thant 12 symbols")
                .occurrences(Occurrences.DAY)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
    }

    public String transformChallengeRequestToJsonString(ChallengeRequest challengeRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(challengeRequest);
    }
}
