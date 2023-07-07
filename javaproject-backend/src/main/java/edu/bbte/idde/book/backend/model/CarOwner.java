package edu.bbte.idde.book.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CarOwner extends BaseEntity {
    private Long id;
    private String name;
    private String nationality;
    private Integer phoneNumber;
    private String personalEmail;
}
