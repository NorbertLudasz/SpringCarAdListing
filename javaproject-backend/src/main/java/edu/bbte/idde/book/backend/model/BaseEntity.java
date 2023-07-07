package edu.bbte.idde.book.backend.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
    protected Long id;
}
