package com.challenger.demo.security.configuration;

import com.challenger.demo.users.UserRepository;
import com.challenger.demo.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Profile("prd")
@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        return userByEmail.map(user -> new CustomUserDetails(userByEmail.get()))
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with mail " + email));
    }
}
