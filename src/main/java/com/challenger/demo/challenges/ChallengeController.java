package com.challenger.demo.challenges;

import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ChallengeController {

    @Autowired
    private ChallengeService challengeService;

    @GetMapping("/challenges")
    public List<ChallengeDatabaseModel> getChallenges() {
        return challengeService.getChallenges();
    }

    @GetMapping("/challenges/{id}")
    public ChallengeDatabaseModel getChallenge(@RequestParam Long id) {
        return challengeService.getChallenge(id);
    }

    @PostMapping("/challenges")
    public ChallengeDatabaseModel createChallenge(@Valid @RequestBody ChallengeDatabaseModel challenge, Errors errors) {
        if(errors.hasErrors()) {
            throw new IllegalArgumentException("Passed parameter challenge is not valid");
        }

        return challengeService.addingChallenge(challenge);
    }

    @DeleteMapping ("/challenges")
    public ChallengeDatabaseModel deleteChallenge(@RequestParam Long id) {
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
