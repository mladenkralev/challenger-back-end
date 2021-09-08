package com.challenger.demo.challenge;

import com.challenger.demo.challenges.Challenge;
import com.challenger.demo.models.embeded.Occurrences;

import java.time.LocalDate;

public class ChallengeUtil {
    static String dummyTitle = "This is dummy title and it longer thant 12 symbols";

    static Challenge createDummyChallenge(String title) {
        return Challenge.builder()
                .title(title)
                .description("This is dummy title and it longer thant 12 symbols")
                .occurrences(Occurrences.DAY)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
    }
}
