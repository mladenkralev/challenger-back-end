package com.challenger.demo.users.models;


import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class UserResponse {

    public String email;
    public String username;

    public Set<ChallengeDatabaseModel> createdByUserChallenges;

    public Set<ChallengeDatabaseModel> assignedToUserChallenges;

    public static UserResponse fromDatabaseEntity(UserDatabaseModel userDatabaseEntity) {
        return UserResponse.builder()
                .email(userDatabaseEntity.getEmail())
                .username(userDatabaseEntity.getUsername())
                //shallow copy
                .createdByUserChallenges(new HashSet<>(userDatabaseEntity.createdByUserChallenges))
                .assignedToUserChallenges(new HashSet<>(userDatabaseEntity.assignedToUserChallenges))
                .build();
    }

}
