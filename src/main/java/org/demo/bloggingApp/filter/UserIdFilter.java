package org.demo.bloggingApp.filter;

import com.google.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.demo.bloggingApp.domain.UserEntity;
import org.demo.bloggingApp.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@AllArgsConstructor(onConstructor_ = @Inject)
public class UserIdFilter implements Filter {

    private static final Logger logger = Logger.getLogger(UserIdFilter.class.getName());

    private final UserRepository userRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        logger.info("UserIdFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String userId = httpRequest.getHeader("X-USER-ID");

        // check if the userId is present in the header
        if (userId == null || userId.isEmpty()) {

            logger.warning("Missing 'userID' in header for request ==> " + httpRequest.getRequestURI());

            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Missing 'userID' in the header");
            return;
        }

        try {

            int userIdInt = Integer.parseInt(userId);
            Optional<UserEntity> userEntity = userRepository.getUserById(userIdInt);

            if (userEntity.isPresent()) {

                logger.info("Request to " + httpRequest.getRequestURI() + " received with 'userID' ==> " + userId);

                filterChain.doFilter(servletRequest, servletResponse);
            } else {

                logger.warning("Invalid 'userID' in header for request ==> " + httpRequest.getRequestURI());

                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpResponse.getWriter().write("Invalid 'userID' in the header");
            }

        } catch (NumberFormatException e) {

            logger.warning("Wrong formatted 'userID' in header for request ==> " + httpRequest.getRequestURI());

            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Wrong formatted 'userID' in the header");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        logger.info("UserIdFilter destroyed.");
    }
}
