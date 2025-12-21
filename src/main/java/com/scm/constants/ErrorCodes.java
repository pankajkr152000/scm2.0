package com.scm.constants;

import com.scm.exception.IErrors;

public enum ErrorCodes implements IErrors {

    USER_EMAIL_ALREADY_EXISTS("USER_EMAIL_ALREADY_EXISTS", "User with this email already exists"),
    USER_PHONE_NUMBER_ALREADY_EXISTS("USER_PHONE_NUMBER_ALREADY_EXISTS", "User with this phone number already exists"),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Resource not found"),
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation failed"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "Something went wrong");

    private final String code;
    private final String message;

    ErrorCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ErrorCodes getError() {
        return this;
    }

}
