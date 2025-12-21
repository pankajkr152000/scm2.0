package com.scm.exception;

import com.scm.constants.ErrorCodes;

public interface IErrors {
    public abstract String getCode();
    public abstract String getMessage();
    public abstract ErrorCodes getError();
}
