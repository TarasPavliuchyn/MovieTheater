package com.epam.spring.theater.dao.impl;

public class UserExistException extends RuntimeException {
    public UserExistException(String message) {
        super(message);
    }
}
