package edu.bbte.idde.book.spring.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CarOwners")
@ToString(callSuper = true)
public class CarOwner extends BaseEntity {
    @NotNull
    private String ownerName;
    private String ownerNationality;
    private Integer ownerPhoneNumber;
    private String ownerPersonalEmail;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,
            mappedBy = "carOwner", orphanRemoval = true)
    private List<CarAd> carAdCollection = new ArrayList<>();

    private Instant requestDate;
}

