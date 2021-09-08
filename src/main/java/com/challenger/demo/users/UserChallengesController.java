package com.challenger.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserChallengesController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users/{user}/challenges")
    public ResponseEntity getAssignedChallenges(@PathVariable String user) {
        Objects.requireNonNull(user, "User path name should be available in the url.");
        user = user.toLowerCase();

        Optional<User> foundUser = userRepository.findUserByEmail(user);
        return foundUser.map(tempUser -> ResponseEntity.ok().body(tempUser.getAssignedToUserChallenges()))
                .orElse(ResponseEntity.notFound().build());
    }

}
