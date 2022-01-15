package com.challenger.demo.util;

import com.challenger.demo.challenges.Challenge;
import com.challenger.demo.models.embeded.Occurrences;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChallengeUtil {
    public static String dummyTitle = "This is dummy title and it longer thant 12 symbols";

    public static Challenge createDummyChallenge(String title) {
        return Challenge.builder()
                .title(title)
                .description("This is dummy title and it longer thant 12 symbols")
                .occurrences(Occurrences.DAY)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
    }
}
