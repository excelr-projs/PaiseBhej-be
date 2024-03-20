package com.paisebhej.Exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomerException extends Exception{
    public CustomerException() {
        // TODO Auto-generated constructor stub
    }

    public CustomerException(String message) {
        // TODO Auto-generated constructor stub
    }
}
