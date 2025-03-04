package com.software.apigateway.exception;

import java.io.Serial;

public class MissingRequestHeaderException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public MissingRequestHeaderException(String message) {
        super("Header Attribute : " + message + " is missing");
    }
}
