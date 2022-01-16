package com.challenger.demo.util;

import com.challenger.demo.users.User;
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

    public static User createDummyUser(String email, String password) {
        return User.builder()
                .id(0)
                .username("test")
                .email(email)
                .password(password)
                .roles("ADMIN_ROLE")
                .active(true)
                .build();
    }

    public static User convertFromStringToJson(String jsonContent) throws JsonProcessingException {
        return objectMapper.readValue(jsonContent, User.class);
    }
}
