package com.scm.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scm.controller.ContactController;
import com.scm.controller.PageController;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice(assignableTypes = {
    ContactController.class,
    PageController.class
})
public class MvcExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleError(HttpSession session) {
        session.setAttribute("errorMessage", "Something went wrong");
        return "error";
    }
}
