package edu.bbte.idde.book.spring.model.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CarOwnerOutNoWhenDto {
    Long id;
    String ownerName;
    String ownerNationality;
    Integer ownerPhoneNumber;
    String ownerPersonalEmail;
}


