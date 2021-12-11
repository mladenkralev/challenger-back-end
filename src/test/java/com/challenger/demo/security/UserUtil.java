package com.challenger.demo.security;

import com.challenger.demo.users.User;

public class UserUtil {

    private UserUtil() {
        throw new IllegalArgumentException("Util method should not be instantiated");
    }

    static User createDummyUser(String email, String encodedPassword) {
        return User.builder()
                .id(0)
                .username("test")
                .password(encodedPassword)
                .email(email)
                .roles("ADMIN")
                .active(true)
                .build();
    }
}
