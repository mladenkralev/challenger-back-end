package com.challenger.demo.util;

import com.challenger.demo.security.controller.AuthenticationRequest;
import com.challenger.demo.users.models.UserRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationRequestUtil {

    @Autowired
    private ObjectMapper objectMapper;

    public AuthenticationRequest createDummyAuthenticationRequest(UserRequest user){
        return AuthenticationRequest.builder()
                .email(user.email)
                .password(user.password)
                .build();
    }

    public String transformRequestToJsonString(AuthenticationRequest authenticationRequest) throws JsonProcessingException {
        return objectMapper.writeValueAsString(authenticationRequest);
    }


}
