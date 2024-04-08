package com.paulomarchon.reactivedorotechtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ProductAlreadyRegisteredException extends RuntimeException {
    public ProductAlreadyRegisteredException(String message) {
        super(message);
    }
}
