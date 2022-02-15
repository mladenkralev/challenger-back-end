package com.challenger.demo.challenges.models;

import com.challenger.demo.models.Badges;
import com.challenger.demo.models.embeded.Occurrences;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeResponse {

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
}
