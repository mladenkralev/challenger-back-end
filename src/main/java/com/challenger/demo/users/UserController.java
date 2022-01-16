package com.challenger.demo.users;

import com.challenger.demo.security.configuration.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        Optional<User> userByEmail = userRepository.findUserByEmail(userDetails.getEmail());
        return ResponseEntity.ok().body(userByEmail.get());

    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @DeleteMapping ("/users")
    public User deleteUser(@RequestParam Integer id) {
        User deletedChallenge = userRepository.getOne(id);
        userRepository.deleteById(id);
        return deletedChallenge;
    }
}
