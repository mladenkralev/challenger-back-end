package com.challenger.demo.util;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Service;

@Service
public class JwtHelper {

    static JacksonJsonParser jsonParser;

    public JwtHelper() {
        jsonParser = new JacksonJsonParser();
    }

    public static String extractJsonTokenFromString(String responseBody) {
        // TODO fix hardcodding!
        return jsonParser.parseMap(responseBody).get("jwt").toString();
    }

}
