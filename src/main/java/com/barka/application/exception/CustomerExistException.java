package com.barka.application.exception;

public class CustomerExistException extends RuntimeException {
    public CustomerExistException(String msg) {
        super(msg);
    }
}