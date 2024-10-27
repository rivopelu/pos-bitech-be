package com.pos.exception;

import com.pos.enums.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException(ResponseEnum msg) {
        super(String.valueOf(msg));
    }

}


