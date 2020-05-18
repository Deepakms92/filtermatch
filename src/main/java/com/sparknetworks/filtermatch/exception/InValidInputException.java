package com.sparknetworks.filtermatch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong input value....")
public class InValidInputException extends RuntimeException {
    public InValidInputException(String message) {
        super(message);
    }
}
