package com.sparknetworks.filtermatch.exception;


/**
 * Exception class for throwing a Invalid input for the search.
 *
 * @author Deepak Srinivas
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong input value....")
public class InValidInputException extends RuntimeException {
    public InValidInputException(String message) {
        super(message);
    }
}
