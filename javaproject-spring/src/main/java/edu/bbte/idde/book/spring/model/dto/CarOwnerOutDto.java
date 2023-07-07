package edu.bbte.idde.book.spring.model.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CarOwnerOutDto {
    Long id;
    String ownerName;
    String ownerNationality;
    Integer ownerPhoneNumber;
    String ownerPersonalEmail;
    Instant requestDate;
}

