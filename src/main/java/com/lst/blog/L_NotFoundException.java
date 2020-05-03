package com.lst.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class L_NotFoundException extends RuntimeException {
    public L_NotFoundException() {

    }

    public L_NotFoundException(String message) {
        super(message);
    }

    public L_NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
