package com.challenger.demo.challenges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping("/challenges")
    public List<Challenge> getChallenges() {
        return challengeService.getChallenges();
    }

    @GetMapping("/challenges/{id}")
    public Challenge getChallenge(@RequestParam Long id) {
        return challengeService.getChallenge(id);
    }

    @PostMapping("/challenges")
    public Challenge createChallenge(@RequestBody Challenge challenge) {
        return challengeService.addingChallenge(challenge);
    }

    @DeleteMapping ("/challenges")
    public Challenge deleteChallenge(@RequestParam Long id) {
        return challengeService.deleteChallenge(id);
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Map<String, String> handleValidationExceptions(
//            MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return errors;
//    }
}
