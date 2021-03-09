package com.example.spring_rest.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Can't find Resource.");
    }
}
