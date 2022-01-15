package com.challenger.demo.util;

import com.challenger.demo.security.controller.AuthenticationRequest;
import com.challenger.demo.users.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationRequestUtil {

    private static ObjectMapper objectMapper;

    @Autowired
    public AuthenticationRequestUtil(ObjectMapper injectedObjectMapper) {
        AuthenticationRequestUtil.objectMapper = injectedObjectMapper;
    }

    public static AuthenticationRequest createDummyAuthenticationRequest(User user){
        return AuthenticationRequest.builder()
                .email(user.email)
                .password(user.password)
                .build();
    }

    public static String transformRequestToJsonString(AuthenticationRequest authenticationRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(authenticationRequest);
    }


}
