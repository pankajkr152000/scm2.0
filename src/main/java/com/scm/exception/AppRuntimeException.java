package com.scm.exception;

import com.scm.constants.ExceptionCollection;

public class AppRuntimeException extends RuntimeException implements IErrors {
    private final ExceptionCollection error;

    public AppRuntimeException(ExceptionCollection error) {
        super(error.getMessage());
        this.error = error;
    }

    @Override
    public String getCode() {
        return error.getCode();
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }

    @Override
    public ExceptionCollection getError() {
        return error;
    }
}
