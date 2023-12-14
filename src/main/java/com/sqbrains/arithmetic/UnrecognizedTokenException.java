package com.sqbrains.arithmetic;

public class UnrecognizedTokenException extends RuntimeException {

    public UnrecognizedTokenException(String token) {
        super(token);
    }
}