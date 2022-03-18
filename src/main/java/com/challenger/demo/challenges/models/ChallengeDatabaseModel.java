package com.challenger.demo.challenges.models;

import com.challenger.demo.models.Badges;
import com.challenger.demo.models.embeded.Occurrences;
import com.challenger.demo.users.models.UserDatabaseModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Table(name= "challenges")
public class ChallengeDatabaseModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;

    @Size(min=12)
    @NotBlank(message = "Title should exist")
    public String title;

    @Size(min=12)
    @NotBlank(message = "Description should exist")
    public String description;
    
    @Enumerated(EnumType.STRING)
    public Occurrences occurrences;

    @OneToMany(mappedBy="badges")
    public Set<Badges> badges;

    public LocalDate startDate;
    public LocalDate endDate;

    public double numberOfProgressHits;

    @JsonIgnore
    @ManyToMany(mappedBy = "createdByUserChallenges")
    Set<UserDatabaseModel> createdByUserChallenges;

    @JsonIgnore
    @ManyToMany(mappedBy = "assignedToUserChallenges")
    Set<UserDatabaseModel> assignedToUserChallenges;
}
