package com.scm.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionService {

    
    /**
     * Logger instance for logging important events and errors.
     */
    private static final Logger log = LoggerFactory.getLogger(SessionService.class);


    @SuppressWarnings({"null", "CallToPrintStackTrace"})
    public static void removeMessage() {
        try{
        HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            System.out.println("Removing message attribute from session!!!");
            session.removeAttribute("message");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error in SessionService" + e);
            if(log.isDebugEnabled()) {
                log.debug("Error in SessionService" + e);
            }
        }
    }

}
