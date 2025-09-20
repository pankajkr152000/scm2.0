package com.scm.exception;

import com.scm.constants.ExceptionCollection;

public interface IErrors {
    public abstract String getCode();
    public abstract String getMessage();
    public abstract ExceptionCollection getError();
    
}
