package com.challenger.demo.models;

import com.challenger.demo.challenges.models.ChallengeDatabaseModel;
import com.challenger.demo.models.embeded.Badge;

import javax.persistence.*;

@Entity
@Table(name="badges")
public class Badges {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JoinColumn(name="challenges_id")
    private ChallengeDatabaseModel badges;

    @Enumerated(EnumType.STRING)
    Badge badge;
}
