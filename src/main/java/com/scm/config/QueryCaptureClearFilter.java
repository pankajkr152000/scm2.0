package com.scm.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Spring filter to clear Hibernate query capture thread-local variables
 * after each HTTP request.
 *
 * <p>This ensures that queries captured during one request do not
 * leak into another request, keeping the UnifiedQueryCapture
 * thread-safe in multi-threaded environments.</p>
 *
 * <p>It works with {@link UnifiedQueryCapture}, which captures
 * INSERT, UPDATE, DELETE, and SELECT queries.</p>
 */
@Component
public class QueryCaptureClearFilter extends OncePerRequestFilter {

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Proceed with request processing
            filterChain.doFilter(request, response);
        } finally {
            // Clear captured queries and parameters for this thread
            UnifiedQueryCapture.clear();
        }
    }
}
