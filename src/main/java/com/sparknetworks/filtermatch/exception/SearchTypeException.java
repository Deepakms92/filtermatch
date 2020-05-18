package com.sparknetworks.filtermatch.exception;

/**
 * Exception class for throwing a Search Type Exception.
 *
 * @author Deepak Srinivas
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong type used for search....")
public class SearchTypeException extends RuntimeException {
    public SearchTypeException(String message) {
        super(message);
    }
}


