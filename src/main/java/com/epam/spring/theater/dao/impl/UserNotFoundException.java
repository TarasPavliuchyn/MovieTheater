package com.epam.spring.theater.dao.impl;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message, Exception ex) {
        super(message, ex);
    }
}
