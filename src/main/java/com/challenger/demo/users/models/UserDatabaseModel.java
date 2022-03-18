package com.challenger.demo.users.models;

import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "users")
public class UserDatabaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String email;
    public String username;
    public boolean active;
    public String roles;
    public String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "created_by_user_challenges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "challenge_id"))
    Set<ChallengeDatabaseModel> createdByUserChallenges;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "assigned_to_user_challenges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "challenge_id"))
    Set<ChallengeDatabaseModel> assignedToUserChallenges;
    public static UserDatabaseModel emptyEntity() {
        return  UserDatabaseModel.builder().build();
    }
}
