package com.challenger.demo.users;

import com.challenger.demo.security.configuration.CustomUserDetails;
import com.challenger.demo.users.models.UserDatabaseModel;
import com.challenger.demo.users.models.UserRequest;
import com.challenger.demo.users.models.UserResponse;
import com.challenger.demo.users.transformers.UserTransformer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserTransformer userTransformer;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        UserRequest userRequest = userTransformer.toUserRequest(userDetails);

        UserDatabaseModel userDatabaseModel = userTransformer.toDatabaseEntity(userRequest);

        UserDatabaseModel userByEmail = userService.findByEmail(userDatabaseModel);

        UserResponse userViewModel = UserResponse.fromDatabaseEntity(userByEmail);

        return ResponseEntity.ok().body(userViewModel);

    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userViewModel) throws JsonProcessingException {
        UserDatabaseModel userDatabaseModel = userTransformer.toDatabaseEntity(userViewModel);
        UserResponse responseViewModel = UserResponse.fromDatabaseEntity(userService.addUser(userDatabaseModel));

        return ResponseEntity.ok().body(responseViewModel);
    }

    @DeleteMapping ("/users")
    public String deleteUser(@RequestParam UserRequest userViewModel) {
        UserDatabaseModel userDatabaseModel = userTransformer.toDatabaseEntity(userViewModel);

        UserDatabaseModel byEmail = userService.findByEmail(userDatabaseModel);

        userService.deleteUser(byEmail);
        return ResponseEntity.ok().toString();
    }


    @GetMapping("/users/{userEmail}/challenges")
    public ResponseEntity getAssignedChallenges(@PathVariable String userEmail) {
        Objects.requireNonNull(userEmail, "User path name should be available in the url.");
        userEmail = userEmail.toLowerCase();

        UserDatabaseModel foundUser = userService.findByEmail(userEmail);
        return ResponseEntity.ok().body(foundUser.getAssignedToUserChallenges());

    }
}
