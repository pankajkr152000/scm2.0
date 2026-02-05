package com.scm.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scm.controller.ContactController;
import com.scm.controller.PageController;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(assignableTypes = {
    ContactController.class,
    PageController.class
})
@Slf4j
public class MvcExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleError(HttpSession session, Exception ex) {
        try {
            
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTrace = sw.toString();
            session.setAttribute("errorMessage", stackTrace);
        }
        return "error";
    }
}
