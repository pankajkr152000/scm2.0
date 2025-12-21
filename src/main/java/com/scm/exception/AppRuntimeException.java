package com.scm.exception;

import com.scm.constants.ErrorCodes;

public class AppRuntimeException extends RuntimeException implements IErrors {
    private final ErrorCodes error;

    public AppRuntimeException(ErrorCodes error) {
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
    public ErrorCodes getError() {
        return error;
    }
}
