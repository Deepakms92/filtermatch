package com.sparknetworks.filtermatch.exception;


/**
 * Exception class for throwing a Search Exception generic type.
 *
 * @author Deepak Srinivas
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Wrong value used for search....")
public class SearchException extends RuntimeException {
    public SearchException(String message) {
        super(message);
    }
}
