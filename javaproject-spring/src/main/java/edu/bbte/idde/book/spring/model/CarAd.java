package edu.bbte.idde.book.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CarAds")
@ToString(callSuper = true)
public class CarAd extends BaseEntity {
    //@JoinColumn(name = "ownerID", nullable = false)
    @JsonIgnore
    @ManyToOne(optional = false)
    private CarOwner carOwner;
    //private Long id;
    private String model;
    private String manufacturer;
    private Integer price;
    private String quality;
    private Integer yearOfMake;
    //private Long ownerID;
}