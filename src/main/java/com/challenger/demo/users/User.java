package com.challenger.demo.users;

import com.challenger.demo.challenges.Challenge;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String email;
    public String username;
    public boolean active;
    public String roles;

    // TODO should be a char array
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "created_by_user_challenges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "challenge_id"))
    Set<Challenge> createdByUserChallenges;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "assigned_to_user_challenges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "challenge_id"))
    Set<Challenge> assignedToUserChallenges;
}
