package com.challenger.demo.challenges;

import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeDatabaseModel, Long> {
}
