package com.mkrejci.rateabeer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BeerNotFoundException extends RuntimeException{
    public BeerNotFoundException(String message) {
        super(message);
    }
}
