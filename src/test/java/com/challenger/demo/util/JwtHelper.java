package com.challenger.demo.util;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class JwtHelper {

    private JacksonJsonParser jsonParser;


    public String extractJsonTokenFromString(String responseBody) {
        // TODO fix hardcodding!
        return jsonParser.parseMap(responseBody).get("jwt").toString();
    }

    @Bean
    public JacksonJsonParser createJsonParser(){
        return jsonParser = new JacksonJsonParser();
    }

}
