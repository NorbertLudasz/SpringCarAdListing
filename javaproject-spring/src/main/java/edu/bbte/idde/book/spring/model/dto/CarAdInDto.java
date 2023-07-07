package edu.bbte.idde.book.spring.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class CarAdInDto {
    @NotNull
    @Positive
    Integer id;
    @NotNull
    @Length(max = 50)
    String model;
    @NotNull
    @Length(max = 50)
    String manufacturer;
    @NotNull
    @Positive
    Integer price;
    @NotNull
    @Length(max = 50)
    String quality;
    @NotNull
    @Positive
    Integer yearOfMake;
    @NotNull
    @Positive
    Long ownerID;
}

