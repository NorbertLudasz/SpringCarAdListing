package edu.bbte.idde.book.spring.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Data
public class CarOwnerInNoWhenDto {
    @NotNull
    @Positive
    Integer id;
    @NotNull
    @Length(max = 50)
    String ownerName;
    @NotNull
    @Length(max = 50)
    String ownerNationality;
    @NotNull
    @Positive
    Integer ownerPhoneNumber;
    @NotNull
    @Length(max = 50)
    String ownerPersonalEmail;
}


