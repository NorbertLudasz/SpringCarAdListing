package edu.bbte.idde.book.spring.model.dto;

import edu.bbte.idde.book.spring.model.CarOwner;
import lombok.Data;

@Data
public class CarAdOutDto {
    Long id;
    String model;
    String manufacturer;
    Integer price;
    String quality;
    Integer yearOfMake;
    //Long ownerID;
    //CarOwnerOutDto carOwner;
    CarOwner carOwner;
}

