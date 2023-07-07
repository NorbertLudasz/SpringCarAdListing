package edu.bbte.idde.book.spring.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NotFoundException extends RuntimeException {
    private String message;

}
