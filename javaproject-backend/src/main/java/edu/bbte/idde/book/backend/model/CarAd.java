package edu.bbte.idde.book.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CarAd extends BaseEntity {
    private Long id;
    private String model;
    private String manufacturer;
    private Integer price;
    private String quality;
    private Integer yearOfMake;
    private Long ownerID;
}