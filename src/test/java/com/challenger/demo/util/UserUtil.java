package com.challenger.demo.util;

import com.challenger.demo.users.models.UserDatabaseModel;
import com.challenger.demo.users.models.UserRequest;
import com.challenger.demo.users.models.UserResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUtil {

    private static ObjectMapper objectMapper;

    @Autowired
    public UserUtil(ObjectMapper injectedObjectMapper) {
        UserUtil.objectMapper = injectedObjectMapper;
    }

    public static UserRequest createDummyUser(String email, String password) {
        return UserRequest.builder()
                .username("test")
                .email(email)
                .password(password)
                .build();
    }

    public static UserResponse convertFromStringToJson(String jsonContent) throws JsonProcessingException {
        return objectMapper.readValue(jsonContent, UserResponse.class);
    }
}
