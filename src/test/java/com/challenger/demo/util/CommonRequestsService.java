package com.challenger.demo.util;

import com.challenger.demo.security.controller.AuthenticationRequest;
import com.challenger.demo.users.models.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Service
public class CommonRequestsService {

    @Autowired
    AuthenticationRequestUtil authenticationRequestUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtHelper jwtHelper;

    public HttpHeaders addAuthorizationHeader(String responseBody) {
        HttpHeaders authHeader = new HttpHeaders();
        authHeader.add(HttpHeaders.AUTHORIZATION, "Bearer " +  jwtHelper.extractJsonTokenFromString(responseBody));
        return authHeader;
    }

    public ResultActions executeAuthentication(UserRequest user) throws Exception {
        AuthenticationRequest authenticationRequest = authenticationRequestUtil.createDummyAuthenticationRequest(user);
        String jsonAuthUserBody = authenticationRequestUtil.transformAuthenticationRequestToJsonString(authenticationRequest);

        return mockMvc.perform(post("/api/v1/authenticate")
                .content(jsonAuthUserBody)
                .contentType("application/json"));
    }
}
