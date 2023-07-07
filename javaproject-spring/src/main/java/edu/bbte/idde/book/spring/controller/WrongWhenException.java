package edu.bbte.idde.book.spring.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WrongWhenException extends RuntimeException {
    private String message;

}
