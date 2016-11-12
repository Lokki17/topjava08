package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class EmptyFieldsException extends RuntimeException{
    public EmptyFieldsException(String message) {
        super(message);
    }
}
