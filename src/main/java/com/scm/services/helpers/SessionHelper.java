package com.scm.services.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    
    /**
     * Logger instance for logging important events and errors.
     */
    private static final Logger log = LoggerFactory.getLogger(SessionHelper.class);


    @SuppressWarnings({"null", "CallToPrintStackTrace"})
    public static void removeMessage() {
        try{
        HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("message");
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error in SessionHelper" + e);
            if(log.isDebugEnabled()) {
                log.debug("Error in SessionHelper" + e);
            }
        }
    }

}
