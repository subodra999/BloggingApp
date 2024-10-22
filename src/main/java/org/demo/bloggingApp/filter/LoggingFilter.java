package org.demo.bloggingApp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.logging.Logger;

public class LoggingFilter implements Filter {

    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        logger.info("LoggingFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String path = httpRequest.getRequestURI();
        long startTime = System.currentTimeMillis();

        logger.info("Request to " + path + " received at " + startTime);

        filterChain.doFilter(servletRequest, servletResponse);

        long duration = System.currentTimeMillis() - startTime;

        logger.info("Request to " + path + " processed in " + duration + " ms");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        logger.info("LoggingFilter destroyed.");
    }
}
