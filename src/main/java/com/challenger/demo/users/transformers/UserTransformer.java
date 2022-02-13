package com.challenger.demo.users.transformers;

import com.challenger.demo.security.configuration.CustomUserDetails;
import com.challenger.demo.users.models.UserDatabaseModel;
import com.challenger.demo.users.models.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserTransformer {

    @Autowired
    PasswordEncoder encoder;

    public UserDatabaseModel toDatabaseEntity(UserRequest userRequest) {
        return UserDatabaseModel.builder()
                .email(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()))
                .username(userRequest.getUsername())
                .active(true)
                .roles("ADMIN")
                //shallow copy
                .createdByUserChallenges(new HashSet<>())
                .assignedToUserChallenges(new HashSet<>())
                .build();
    }
    public UserRequest toUserRequest(CustomUserDetails userDetails) {
        return UserRequest.builder()
                .username(userDetails.getUsername())
                .password(userDetails.getPassword())
                .email(userDetails.getEmail())
                .build();
    }
}
