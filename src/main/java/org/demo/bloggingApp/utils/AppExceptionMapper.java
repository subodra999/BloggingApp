package org.demo.bloggingApp.utils;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.demo.bloggingApp.error.AppException;

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {

        @Override
        public Response toResponse(AppException exception) {
            return Response.status(exception.getError().getStatusCode())
                    .entity(exception.getError())
                    .build();
        }
}
