package ru.javawebinar.topjava.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class EmptyFieldsException extends RuntimeException{
    public EmptyFieldsException(String message) {
        super(message);
    }
}
